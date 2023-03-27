package com.test.Insomnia.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Order_Table") // 訂單
public class Order {

	// 未放
	// creditCardId int not null, -- fk to creditCard

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	// 訂單(多方)與信用卡(雙向)
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "creditCard")
//	@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_creditCard", foreignKey = @ForeignKey(name = "fk_Order_creditCard"), nullable = false)
	private CreditCard creditCard;

	// 先設定一訂單買一種方案 BY顏
	// 訂單(多方)與購買方案(雙向)。
//	@JsonBackReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlan")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "fk_PurchasePlan", foreignKey = @ForeignKey(name = "fk_Order_PurchasePlan"), nullable = false)
	private PurchasePlan purchasePlan;

//	@JsonBackReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "areaId")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "fk_Area", foreignKey = @ForeignKey(name = "fk_Order_Area"), nullable = false)
	private Area areaId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fK_UserId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_order"), nullable = false)
	private User userId;

	// 時間部分不確定
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8")
	@Column(columnDefinition = "date default current_timestamp", nullable = false)
	private Date orderDate;

	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer shippingstatus;
	public static final Map<Integer, String> SHIPPING_OPTIONS = new HashMap<>() {

		private static final long serialVersionUID = 1L;

		{
			put(1, OrderShippingConstants.NOTYET);
			put(2, OrderShippingConstants.SHIPPED);
			put(3, OrderShippingConstants.ARRIVED);
		}
	};

	@Column(columnDefinition = "int check (amount>0)", nullable = false)
	private Integer amount;

	@Column(columnDefinition = "int check (price>0)", nullable = false)
	private Integer price;

	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer paymentStatus;

	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String note;

	// 先訂為運送完整地址，大洲分區在area BY顏
	// 要設定成不能null嗎?
	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String detailAddr;
	
	// 取消狀態對應數字未定 BY顏
	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer cancelStatus;

	// 下訂時自動生成時間
	@PrePersist
	public void onCreate() {
		if (cancelStatus == null) {
			cancelStatus = 0;
		}
		if (orderDate == null) {
			orderDate = new Date();
		}
		if (shippingstatus == null) {
			shippingstatus = 0;
		}
		if (paymentStatus == null) {
			paymentStatus = 0;
		}
	}
	
	public Order() {
	}

	public Order(Integer orderId, CreditCard creditCard, PurchasePlan purchasePlan, Area areaId, User userId,
			Date orderDate, Integer shippingstatus, Integer amount, Integer price, Integer paymentStatus, String note,
			String detailAddr, Integer cancelStatus) {
		super();
		this.orderId = orderId;
		this.creditCard = creditCard;
		this.purchasePlan = purchasePlan;
		this.areaId = areaId;
		this.userId = userId;
		this.orderDate = orderDate;
		this.shippingstatus = shippingstatus;
		this.amount = amount;
		this.price = price;
		this.paymentStatus = paymentStatus;
		this.note = note;
		this.detailAddr = detailAddr;
		this.cancelStatus = cancelStatus;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public Area getAreaId() {
		return areaId;
	}

	public void setAreaId(Area areaId) {
		this.areaId = areaId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getShippingstatus() {
		return shippingstatus;
	}

	public void setShippingstatus(Integer shippingstatus) {
		this.shippingstatus = shippingstatus;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public Integer getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Integer cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
