package itaf.framework.openfire.plugin;

import itaf.framework.openfire.plugin.service.IIntelligenceOrderService;
import itaf.framework.openfire.plugin.service.impl.IntelligenceOrderServiceImpl;

import java.io.File;
import java.util.List;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

/**
 * 
 * 智能订单插件
 * 
 * @author
 * 
 * @UpdateDate 2014年9月15日
 */
public class IntelligenceOrderPlugin implements PacketInterceptor, Plugin {

	private static final Logger log = LoggerFactory
			.getLogger(IntelligenceOrderPlugin.class);

	// Hook for intercpetorn
	private InterceptorManager interceptorManager;
	private static PluginManager pluginManager;
	private static IIntelligenceOrderService intelligenceOrderService;
	private static boolean DEBUG = false;

	public IntelligenceOrderPlugin() {
		interceptorManager = InterceptorManager.getInstance();
		intelligenceOrderService = new IntelligenceOrderServiceImpl();
	}

	/**
	 * <b>function:</b> 拦截消息核心方法，Packet就是拦截消息对象
	 * 
	 */
	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {
		if (session != null) {
			debug(packet, incoming, processed, session);
		}

		JID recipient = packet.getTo();
		if (recipient != null) {
			String username = recipient.getNode();
			// 广播消息或是不存在/没注册的用户.
			if (username == null
					|| !UserManager.getInstance().isRegisteredUser(recipient)) {
				return;
			} else if (!XMPPServer.getInstance().getServerInfo()
					.getXMPPDomain().equals(recipient.getDomain())) {
				// 非当前openfire服务器信息
				return;
			} else if ("".equals(recipient.getResource())) {

			}
		}
		this.doAction(packet, incoming, processed, session);
	}

	/**
	 * <b>function:</b> 执行分析聊天记录动作
	 * 
	 * @param packet
	 *            数据包
	 * @param incoming
	 *            true表示发送方
	 * @param session
	 *            当前用户session
	 */
	private void doAction(Packet packet, boolean incoming, boolean processed,
			Session session) {
		Packet copyPacket = packet.createCopy();
		if (packet instanceof Message) {
			Message message = (Message) copyPacket;

			// 一对一聊天，单人模式
			if (message.getType() == Message.Type.chat) {
				// log.info("单人聊天信息：{}", message.toXML());
				// debug("单人聊天信息：" + message.toXML());

				// 程序执行中；是否为结束或返回状态（是否是当前session用户发送消息）
				if (processed || !incoming) {
					return;
				}
				intelligenceOrderService.analysisMessage((Message) packet);

				// 群聊天，多人模式
			} else if (message.getType() == Message.Type.groupchat) {
				List<?> els = message.getElement().elements("x");
				if (els != null && !els.isEmpty()) {
					// log.info("群聊天信息：{}", message.toXML());
					// debug("群聊天信息：" + message.toXML());
				} else {
					// log.info("群系统信息：{}", message.toXML());
					// debug("群系统信息：" + message.toXML());
				}

				// 其他信息
			} else {
				// log.info("其他信息：{}", message.toXML());
				// debug("其他信息：" + message.toXML());
			}
		} else if (packet instanceof IQ) {
			IQ iq = (IQ) copyPacket;
			if (iq.getType() == IQ.Type.set && iq.getChildElement() != null
					&& "session".equals(iq.getChildElement().getName())) {
				// log.info("用户登录成功：{}", iq.toXML());
				// debug("用户登录成功：" + iq.toXML());
			}
		} else if (packet instanceof Presence) {
			Presence presence = (Presence) copyPacket;
			if (presence.getType() == Presence.Type.unavailable) {
				// log.info("用户退出服务器成功：{}", presence.toXML());
				// debug("用户退出服务器成功：" + presence.toXML());
			}
		}
	}

	/**
	 * <b>function:</b> 调试信息
	 * 
	 * @param packet
	 *            数据包
	 * @param incoming
	 *            如果为ture就表明是发送者
	 * @param processed
	 *            执行
	 * @param session
	 *            当前用户session
	 */
	private void debug(Packet packet, boolean incoming, boolean processed,
			Session session) {

		if (!DEBUG) {
			return;
		}

		String info = "[ packetID: " + packet.getID() + ", to: "
				+ packet.getTo() + ", from: " + packet.getFrom()
				+ ", incoming: " + incoming + ", processed: " + processed
				+ " ]";

		long timed = System.currentTimeMillis();
		debug("################### start ###################" + timed);
		debug("id:" + session.getStreamID() + ", address: "
				+ session.getAddress());
		debug("info: " + info);
		debug("xml: " + packet.toXML());
		debug("################### end #####################" + timed);

		log.info("id:" + session.getStreamID() + ", address: "
				+ session.getAddress());
		log.info("info: {}", info);
		log.info("plugin Name: " + pluginManager.getName(this) + ", xml: "
				+ packet.toXML());
	}

	private void debug(Object message) {
		if (true) {
			System.out.println(message);
		}
	}

	@Override
	public void destroyPlugin() {
		interceptorManager.removeInterceptor(this);
		debug("销毁智能订单插件成功！");
	}

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		interceptorManager.addInterceptor(this);
		pluginManager = manager;
		debug("安装智能订单插件成功！");
	}
}