package com.test.Insomnia.model;

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PurchasePlan_Table") // 此專案的購買方案 BY顏
public class PurchasePlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer purchasePlanId;

	// 【purchasePlanItemsPK】.purchasePlanIdPK 是屬性名
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlanItems")
	@JsonIgnore
	@OneToMany(mappedBy = "purchasePlanItemsPK.purchasePlanIdPK", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<PurchasePlanItems> purchasePlanItems = new HashSet<PurchasePlanItems>(0);

//	@JsonManagedReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "order")
	// 去除cascade.remove(不可刪order)
	@JsonIgnore
	@OneToMany(mappedBy = "purchasePlan", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH }, orphanRemoval = false) 
//	private Set<Order> order = new LinkedHashSet<>();
	private Set<Order> order = new HashSet<>(0);

	@ManyToMany(cascade = CascadeType.ALL) // 預設有lazy
	@JsonIgnore
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "areaNo")
	@JoinTable(name = "shippingArea", joinColumns = { // 本類別
			@JoinColumn(name = "FK_purchasePlanId", referencedColumnName = "purchasePlanId", foreignKey = @ForeignKey(name = "FK_pur_are")) }, inverseJoinColumns = { // 對照類別
					@JoinColumn(name = "FK_areaId", referencedColumnName = "areaId", foreignKey = @ForeignKey(name = "FK_are_pur")) })
	private Set<Area> areaNo = new HashSet<Area>(0); // 不知道為甚麼用list就只能建出雙FK的中介表格(?
	
	// 修改product-purchasePlan的entity為雙向
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FK_ProductId", foreignKey = @ForeignKey(name = "fk_ProductId_purchasePlan"), nullable = false)
	private Product product;

	@Nationalized
	@Column(nullable = false, length = 255)
	private String title;

	// 購買方案介紹 BY顏
	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String intro;

	@Column(columnDefinition = "int check (price>0)", nullable = false)
	private Integer price;

	@Column(columnDefinition = "int check (minOrderAmount>0)")
	private Integer minOrderAmount;

	@Column(columnDefinition = "int check (maxOrderAmount>0)")
	private Integer maxOrderAmount;

//	@Column(columnDefinition = "int default 0 check (currentOrderAmount>=0)", nullable = false)
//	private Integer currentOrderAmount = 0;

	// 時間不確定...
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shipmentDate;

	public PurchasePlan() {
	}

	public PurchasePlan(Integer purchasePlanId, Set<PurchasePlanItems> purchasePlanItems, Set<Order> order,
			Set<Area> areaNo, Product product, String title, String intro, Integer price, Integer minOrderAmount,
			Integer maxOrderAmount, Integer currentOrderAmount, Date shipmentDate) {
		super();
		this.purchasePlanId = purchasePlanId;
		this.purchasePlanItems = purchasePlanItems;
		this.order = order;
		this.areaNo = areaNo;
		this.product = product;
		this.title = title;
		this.intro = intro;
		this.price = price;
		this.minOrderAmount = minOrderAmount;
		this.maxOrderAmount = maxOrderAmount;
//		this.currentOrderAmount = currentOrderAmount;
		this.shipmentDate = shipmentDate;
	}

	public Integer getPurchasePlanId() {
		return purchasePlanId;
	}

	public void setPurchasePlanId(Integer purchasePlanId) {
		this.purchasePlanId = purchasePlanId;
	}

	public Set<PurchasePlanItems> getPurchasePlanItems() {
		return purchasePlanItems;
	}

	public void setPurchasePlanItems(Set<PurchasePlanItems> purchasePlanItems) {
		this.purchasePlanItems = purchasePlanItems;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	public Set<Area> getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(Set<Area> areaNo) {
		this.areaNo = areaNo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(Integer minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	public Integer getMaxOrderAmount() {
		return maxOrderAmount;
	}

	public void setMaxOrderAmount(Integer maxOrderAmount) {
		this.maxOrderAmount = maxOrderAmount;
	}

//	public Integer getCurrentOrderAmount() {
//		return currentOrderAmount;
//	}
//
//	public void setCurrentOrderAmount(Integer currentOrderAmount) {
//		this.currentOrderAmount = currentOrderAmount;
//	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

}
