package com.test.Insomnia.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Item_Table")
public class Item {		//此專案下的貨品列表 BY顏

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;

	@Nationalized
	@Column(nullable = false, length = 255)
	private String itemName;

	// 對應此購買方案的產品列表 BY顏
	//purchasePlanItemsPK.itemIdPK：purchasePlanItemsPK (用來放雙主鍵) 的 itemIdPK 屬性 BY顏
//	@JsonManagedReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlanItems")
	@JsonIgnore
	@OneToMany(mappedBy = "purchasePlanItemsPK.itemIdPK", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<PurchasePlanItems> purchasePlanItems = new HashSet<PurchasePlanItems>(0);

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fK_ProductId", referencedColumnName = "PId", foreignKey = @ForeignKey(name = "fk_ProductId_Item"), nullable = false)
	private Product productId;

	public Item() {
	}

	public Item(Integer itemId, String itemName, Set<PurchasePlanItems> purchasePlanItems, Product productId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.purchasePlanItems = purchasePlanItems;
		this.productId = productId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Set<PurchasePlanItems> getPurchasePlanItems() {
		return purchasePlanItems;
	}

	public void setPurchasePlanItems(Set<PurchasePlanItems> purchasePlanItems) {
		this.purchasePlanItems = purchasePlanItems;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}
}
