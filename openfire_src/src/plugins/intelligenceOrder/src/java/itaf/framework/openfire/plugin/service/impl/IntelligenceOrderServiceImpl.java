package itaf.framework.openfire.plugin.service.impl;

import itaf.framework.openfire.plugin.IntelligenceOrderDBManager;
import itaf.framework.openfire.plugin.domain.BzCartItemTemp;
import itaf.framework.openfire.plugin.domain.BzProduct;
import itaf.framework.openfire.plugin.domain.BzProductAttachment;
import itaf.framework.openfire.plugin.domain.SysUser;
import itaf.framework.openfire.plugin.service.IIntelligenceOrderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.library.DynamicEntityLibrary;
import org.ansj.splitWord.analysis.DynamicEntityAnalysis;
import org.jivesoftware.openfire.XMPPServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class IntelligenceOrderServiceImpl implements IIntelligenceOrderService {

	@Override
	public void analysisMessage(Message message) {
		if (message == null) {
			return;
		}

		JID fromJID = message.getFrom();

		// 获取消息发送者信息
		SysUser currSysUser = IntelligenceOrderDBManager.getInstance()
				.findSysUser(fromJID.getNode());

		// 消息发送者不是买家
		if (currSysUser == null || currSysUser.getType() != 1L) {
			return;
		}

		JID toJID = message.getTo();

		// 获取商户商品名称
		List<String> productNames = IntelligenceOrderDBManager.getInstance()
				.findBzProductNames(toJID.getNode());

		// 将商户商品名称放入动态实体字典
		for (String productName : productNames) {
			DynamicEntityLibrary.insertWord(productName.toLowerCase(),
					"productName", 1000);
		}

		JSONArray jsonArray = new JSONArray();
		// 识别买家聊天信息里的商品名称
		Map<String, String> termNames = new HashMap<String, String>();
		List<Term> terms = DynamicEntityAnalysis.parse(message.getBody());
		for (Term term : terms) {
			if (!term.getNatureStr().startsWith("productName")) {
				continue;
			}
			if (termNames.containsKey(term.getName())) {
				continue;
			}
			termNames.put(term.getName(), term.getNatureStr());
			// 若获取到商品名称
			// 商品信息
			try {
				BzProduct bzProduct = IntelligenceOrderDBManager.getInstance()
						.findBzProduct(toJID.getNode(), term.getName());
				// 商品下架或删除
				if (bzProduct == null) {
					continue;
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ConsumerId", currSysUser.getId());
				jsonObject.put("ConsumerCode", fromJID.getNode());
				jsonObject.put("MerchantCode", toJID.getNode());

				jsonObject.put("ProductId", bzProduct.getId());
				jsonObject.put("ProductName", bzProduct.getProductName());
				jsonObject.put("CartItemNum", 1L);
				jsonObject
						.put("CartItemUnitPrice", bzProduct.getProductPrice());
				jsonObject.put("CartItemDiscount", new BigDecimal(0));
				jsonObject.put("CartItemPreferential", new BigDecimal(0));
				jsonObject.put("CartItemPrice", bzProduct.getProductPrice());

				// 购物车条目(临时)
				BzCartItemTemp bzCartItemTemp = new BzCartItemTemp();
				bzCartItemTemp.setBzConsumerId(currSysUser.getId());
				bzCartItemTemp.setProductId(bzProduct.getId());
				bzCartItemTemp.setItemNum(1L);
				bzCartItemTemp.setItemUnitPrice(bzProduct.getProductPrice());
				bzCartItemTemp.setItemDiscount(new BigDecimal(0));
				bzCartItemTemp.setItemPreferential(new BigDecimal(0));
				bzCartItemTemp.setItemPrice(bzProduct.getProductPrice());
				bzCartItemTemp.setChattingMessage(message.toXML());

				// 添加购物车条目(临时)
				IntelligenceOrderDBManager.getInstance().addCartItemTemp(
						bzCartItemTemp);

				// 商品附件信息
				BzProductAttachment bzProductAttachment = IntelligenceOrderDBManager
						.getInstance().findBzProductAttachment(
								bzProduct.getId());

				if (bzProductAttachment != null) {
					jsonObject.put("ProductAttachmentId",
							bzProductAttachment.getId());
					jsonObject.put("ProductAttachmentSrcFileName",
							bzProductAttachment.getSrcFileName());
					jsonObject.put("ProductAttachmentFileName",
							bzProductAttachment.getFileName());
					jsonObject.put("ProductAttachmentFileType",
							bzProductAttachment.getFileType());
					jsonObject.put("ProductAttachmentFileExt",
							bzProductAttachment.getFileExt());
				}

				jsonArray.put(jsonObject);
			} catch (Exception ex) {
				ex.printStackTrace();
				//
			}
		}

		// 提醒用户可以下单
		if (jsonArray.length() > 0) {
			XMPPServer
					.getInstance()
					.getSessionManager()
					.sendServerMessage(fromJID, "IntelligenceOrder",
							jsonArray.toString());
		}
	}

}
