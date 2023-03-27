package com.test.Insomnia.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // 不知道怎麼寫卡著中
@Table(name = "PurchasePlanItems_Table") // [購買方案 貨品項目]，不能用hibernate自動產生(???
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlanItemsPK")
public class PurchasePlanItems {

	
//	@JsonManagedReference	//不太確定 BY顏
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlanItemsPK")
	@JsonIgnore
	@EmbeddedId		//雙主鍵table的實體的id(把purchasePlanItemsPK整組拿來當ID) BY顏
	private PurchasePlanItemsPK purchasePlanItemsPK;

	//此購買方案的此品項的數量 BY顏
	@Column(columnDefinition = "int check ( amount > 0 )", nullable = false)
	private Integer amount;

	public PurchasePlanItems() {
	}

	public PurchasePlanItems(PurchasePlanItemsPK purchasePlanItemsPK, Integer amount) {
		super();
		this.purchasePlanItemsPK = purchasePlanItemsPK;
		this.amount = amount;
	}

	public PurchasePlanItemsPK getPurchasePlanItemsPK() {
		return purchasePlanItemsPK;
	}

	public void setPurchasePlanItemsPK(PurchasePlanItemsPK purchasePlanItemsPK) {
		this.purchasePlanItemsPK = purchasePlanItemsPK;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
