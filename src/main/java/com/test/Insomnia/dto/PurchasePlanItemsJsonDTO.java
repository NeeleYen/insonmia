package com.test.Insomnia.dto;

public class PurchasePlanItemsJsonDTO {
	private Integer fk_itemId;
	private String fk_itemName;
	private Integer amount;
	private Integer fk_purchasePlanId;
	
	public Integer getFk_itemId() {
		return fk_itemId;
	}
	public void setFk_itemId(Integer fk_itemId) {
		this.fk_itemId = fk_itemId;
	}
	public String getFk_itemName() {
		return fk_itemName;
	}
	public void setFk_itemName(String fk_itemName) {
		this.fk_itemName = fk_itemName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getFk_purchasePlanId() {
		return fk_purchasePlanId;
	}
	public void setFk_purchasePlanId(Integer fk_purchasePlanId) {
		this.fk_purchasePlanId = fk_purchasePlanId;
	}
	public PurchasePlanItemsJsonDTO() {
		super();
	}
	public PurchasePlanItemsJsonDTO(Integer fk_itemId, String fk_itemName, Integer amount, Integer fk_purchasePlanId) {
		super();
		this.fk_itemId = fk_itemId;
		this.fk_itemName = fk_itemName;
		this.amount = amount;
		this.fk_purchasePlanId = fk_purchasePlanId;
	}
	
}
