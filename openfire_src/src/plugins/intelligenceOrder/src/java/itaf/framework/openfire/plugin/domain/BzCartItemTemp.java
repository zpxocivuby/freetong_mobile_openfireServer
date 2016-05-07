package itaf.framework.openfire.plugin.domain;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 购物车条目临时
 * 
 * @author
 * 
 * @UpdateDate 2014年9月10日
 */
public class BzCartItemTemp {

	private Long id;
	private Long bzConsumerId;
	private Long bzProductId;
	private Long itemNum;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemDiscount;
	private BigDecimal itemPreferential;
	private BigDecimal itemPrice;
	private String chattingMessage;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private Boolean markForDelete = false;

	public BzCartItemTemp() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBzConsumerId() {
		return bzConsumerId;
	}

	public void setBzConsumerId(Long bzConsumerId) {
		this.bzConsumerId = bzConsumerId;
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public Long getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getItemUnitPrice() {
		return this.itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public BigDecimal getItemDiscount() {
		return this.itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getItemPreferential() {
		return this.itemPreferential;
	}

	public void setItemPreferential(BigDecimal itemPreferential) {
		this.itemPreferential = itemPreferential;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getChattingMessage() {
		return this.chattingMessage;
	}

	public void setChattingMessage(String chattingMessage) {
		this.chattingMessage = chattingMessage;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createBy) {
		this.createdBy = createBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createDate) {
		this.createdDate = createDate;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updateBy) {
		this.updatedBy = updateBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updateDate) {
		this.updatedDate = updateDate;
	}

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

}
