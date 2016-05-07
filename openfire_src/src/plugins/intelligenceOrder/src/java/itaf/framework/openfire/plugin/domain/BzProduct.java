package itaf.framework.openfire.plugin.domain;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * 
 * 商品
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class BzProduct  {

	private Long id;
	private Long bzMerchantId;
	private String productName;
	private BigDecimal productPrice;
	private BigDecimal productPurchasePrice;
	private String productColor;
	private String productDescription;
	private Long productOnSale;
	private Long isStockLimitless;
	private Long isShelfLimitless;
	private Long isStockSupport;
	private Long isShelfSupport;

	public BzProduct() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBzMerchantId() {
		return bzMerchantId;
	}

	public void setBzMerchantId(Long bzMerchantId) {
		this.bzMerchantId = bzMerchantId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductPurchasePrice() {
		return this.productPurchasePrice;
	}

	public void setProductPurchasePrice(BigDecimal productPurchasePrice) {
		this.productPurchasePrice = productPurchasePrice;
	}

	public String getProductColor() {
		return this.productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Long getProductOnSale() {
		return this.productOnSale;
	}

	public void setProductOnSale(Long productOnSale) {
		this.productOnSale = productOnSale;
	}

	public Long getIsStockLimitless() {
		return this.isStockLimitless;
	}

	public void setIsStockLimitless(Long isStockLimitless) {
		this.isStockLimitless = isStockLimitless;
	}

	public Long getIsShelfLimitless() {
		return this.isShelfLimitless;
	}

	public void setIsShelfLimitless(Long isShelfLimitless) {
		this.isShelfLimitless = isShelfLimitless;
	}

	public Long getIsStockSupport() {
		return this.isStockSupport;
	}

	public void setIsStockSupport(Long isStockSupport) {
		this.isStockSupport = isStockSupport;
	}

	public Long getIsShelfSupport() {
		return this.isShelfSupport;
	}

	public void setIsShelfSupport(Long isShelfSupport) {
		this.isShelfSupport = isShelfSupport;
	}

}
