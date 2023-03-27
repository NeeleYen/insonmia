package com.test.Insomnia.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Embeddable // 設定複合主鍵
public class PurchasePlanItemsPK implements Serializable {

	private static final long serialVersionUID = 1L;

//	@JsonBackReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemIdPK")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_itemId", foreignKey = @ForeignKey(name = "fk_itemId_Pur"))
	private Item itemIdPK;

//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlanIdPK")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_purchasePlanId", foreignKey = @ForeignKey(name = "fk_PlanId_Pur"))
	private PurchasePlan purchasePlanIdPK;

	// 可能還要重設equals、hashCode(? 不確定

	
	
	public PurchasePlanItemsPK() {

	}
	

	public PurchasePlanItemsPK(Item itemIdPK, PurchasePlan purchasePlanIdPK) {
		super();
		this.itemIdPK = itemIdPK;
		this.purchasePlanIdPK = purchasePlanIdPK;
	}

	public Item getItemIdPK() {
		return itemIdPK;
	}

	public void setItemIdPK(Item itemIdPK) {
		this.itemIdPK = itemIdPK;
	}

	public PurchasePlan getPurchasePlanIdPK() {
		return purchasePlanIdPK;
	}

	public void setPurchasePlanIdPK(PurchasePlan purchasePlanIdPK) {
		this.purchasePlanIdPK = purchasePlanIdPK;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemIdPK, purchasePlanIdPK);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchasePlanItemsPK other = (PurchasePlanItemsPK) obj;
		return Objects.equals(itemIdPK, other.itemIdPK) && Objects.equals(purchasePlanIdPK, other.purchasePlanIdPK);
	}
	
}
