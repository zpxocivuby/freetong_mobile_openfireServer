package itaf.framework.openfire.plugin;

import itaf.framework.openfire.plugin.domain.BzCartItemTemp;
import itaf.framework.openfire.plugin.domain.BzProduct;
import itaf.framework.openfire.plugin.domain.BzProductAttachment;
import itaf.framework.openfire.plugin.domain.SysUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 数据库管理
 * 
 * @author
 * 
 * @UpdateDate 2014年9月15日
 */
public class IntelligenceOrderDBManager {

	private static final Logger logger = LoggerFactory
			.getLogger(IntelligenceOrderDBManager.class);

	private static final String BZPRODUCT_PRODUCTNAME_MERCHANT = "SELECT p.PRODUCT_NAME FROM BZ_PRODUCT p,SYS_USER u WHERE p.MARK_FOR_DELETE=0 AND p.PRODUCT_ON_SALE=1 AND p.BZ_MERCHANT_ID=u.SYS_USER_ID AND u.USERNAME=?";
	private static final String BZPRODUCT_QUERY = "SELECT p.* FROM BZ_PRODUCT p,SYS_USER u WHERE p.MARK_FOR_DELETE=0 AND p.PRODUCT_ON_SALE=1 AND p.BZ_MERCHANT_ID=u.SYS_USER_ID AND u.USERNAME=? AND p.PRODUCT_NAME=?";
	private static final String BZPRODUCTATTACHMENT_QUERY = "SELECT p.* FROM BZ_PRODUCT_ATTACHMENT p WHERE p.MARK_FOR_DELETE=0 AND  p.BZ_PRODUCT_ID=?";

	private static final String SYSUSER_QUERY = "SELECT u.* FROM SYS_USER u WHERE u.USERNAME=?";

	private static final String BZCARTITEM_INSERT = "INSERT INTO BZ_CART_ITEM_TEMP(SYS_USER_ID, BZ_PRODUCT_ID, ITEM_NUM, ITEM_UNIT_PRICE, ITEM_DISCOUNT, ITEM_PREFERENTIAL, ITEM_PRICE, CHATTING_MESSAGE, CREATE_BY, CREATE_DATE) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private static final IntelligenceOrderDBManager DB_MANAGER = new IntelligenceOrderDBManager();

	public static IntelligenceOrderDBManager getInstance() {
		return DB_MANAGER;
	}

	public SysUser findSysUser(String username) {
		SysUser sysUser = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(SYSUSER_QUERY);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sysUser = new SysUser();
				sysUser.setId(rs.getLong("SYS_USER_ID"));
				sysUser.setUsername(rs.getString("USERNAME"));
				sysUser.setPassword(rs.getString("PASSWORD"));
				sysUser.setMobile(rs.getString("MOBILE"));
				sysUser.setType(rs.getLong("TYPE"));
			}
			rs.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			logger.error(sqle.getMessage(), sqle);
		} finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
		return sysUser;
	}

	public BzProduct findBzProduct(String merchantName, String productName) {
		BzProduct bzProduct = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(BZPRODUCT_QUERY);
			pstmt.setString(1, merchantName);
			pstmt.setString(2, productName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bzProduct = new BzProduct();
				bzProduct.setId(rs.getLong("BZ_PRODUCT_ID"));
				bzProduct.setBzMerchantId(rs.getLong("BZ_MERCHANT_ID"));
				bzProduct.setProductName(rs.getString("PRODUCT_NAME"));
				bzProduct.setProductPrice(rs.getBigDecimal("PRODUCT_PRICE"));
				bzProduct.setProductPurchasePrice(rs
						.getBigDecimal("PRODUCT_PURCHASE_PRICE"));
				bzProduct.setProductColor(rs.getString("PRODUCT_COLOR"));
				bzProduct.setProductDescription(rs
						.getString("PRODUCT_DESCRIPTION"));
				bzProduct.setProductOnSale(rs.getLong("PRODUCT_ON_SALE"));
				bzProduct.setIsStockLimitless(rs.getLong("IS_STOCK_LIMITLESS"));
				bzProduct.setIsShelfLimitless(rs.getLong("IS_SHELF_LIMITLESS"));
				bzProduct.setIsStockSupport(rs.getLong("IS_STOCK_SUPPORT"));
				bzProduct.setIsShelfSupport(rs.getLong("IS_SHELF_SUPPORT"));
			}
			rs.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			logger.error(sqle.getMessage(), sqle);
		} finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
		return bzProduct;
	}

	public BzProductAttachment findBzProductAttachment(Long productId) {
		BzProductAttachment bzProductAttachment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(BZPRODUCTATTACHMENT_QUERY);
			pstmt.setLong(1, productId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bzProductAttachment = new BzProductAttachment();
				bzProductAttachment.setId(rs
						.getLong("BZ_PRODUCT_ATTACHMENT_ID"));
				bzProductAttachment.setBzProductId(rs.getLong("BZ_PRODUCT_ID"));
				bzProductAttachment.setSrcFileName(rs
						.getString("SRC_FILE_NAME"));
				bzProductAttachment.setFileName(rs.getString("FILE_NAME"));
				bzProductAttachment.setFileType(rs.getLong("FILE_TYPE"));
				bzProductAttachment.setFileExt(rs.getString("FILE_EXT"));
			}
			rs.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			logger.error(sqle.getMessage(), sqle);
		} finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
		return bzProductAttachment;
	}

	public List<String> findBzProductNames(String merchantName) {
		List<String> productNames = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(BZPRODUCT_PRODUCTNAME_MERCHANT);
			pstmt.setString(1, merchantName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				productNames.add(rs.getString("PRODUCT_NAME"));
			}
			rs.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			logger.error(sqle.getMessage(), sqle);
		} finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
		return productNames;
	}

	public boolean addCartItemTemp(BzCartItemTemp bzCartItemTemp) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(BZCARTITEM_INSERT);
			int i = 1;
			pstmt.setLong(i++, bzCartItemTemp.getBzConsumerId());
			pstmt.setLong(i++, bzCartItemTemp.getBzProductId());
			pstmt.setLong(i++, bzCartItemTemp.getItemNum());
			pstmt.setBigDecimal(i++, bzCartItemTemp.getItemUnitPrice());
			pstmt.setBigDecimal(i++, bzCartItemTemp.getItemDiscount());
			pstmt.setBigDecimal(i++, bzCartItemTemp.getItemPreferential());
			pstmt.setBigDecimal(i++, bzCartItemTemp.getItemPrice());
			pstmt.setString(i++, bzCartItemTemp.getChattingMessage());
			pstmt.setLong(i++, 1L);
			pstmt.setTimestamp(i++, new Timestamp(new Date().getTime()));
			return pstmt.execute();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			logger.error(sqle.getMessage(), sqle);
			return false;
		} finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
	}
}
