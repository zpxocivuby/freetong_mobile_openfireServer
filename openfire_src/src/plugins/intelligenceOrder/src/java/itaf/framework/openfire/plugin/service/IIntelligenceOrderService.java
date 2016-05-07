package itaf.framework.openfire.plugin.service;

import org.xmpp.packet.Message;

public interface IIntelligenceOrderService {

	void analysisMessage(Message message);

}
