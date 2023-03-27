package com.test.Insomnia.model;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CreditCard_Table")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer creditCardId;

	// @JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Order> order = new HashSet<Order>(0);

	// 卡號顯示(前碼/末幾碼)，卡號固定16碼 //要限制長度嗎？BY顏
	// https://blog.csdn.net/wohaqiyi/article/details/79803229
	@Column(nullable = false)
	private Long cardNumber;

	@Column(nullable = false)
	private Integer cvc;

	// 信用卡可能需要防呆(過期)，請參考這~
	// https://vladmihalcea.com/java-yearmonth-jpa-hibernate/
	// https://www.w3cschool.cn/java/codedemo-484047538.html
	@Column(columnDefinition = "Date", nullable = false)
	@Convert(converter = YearMonthDateAttributeConverter.class)
	private YearMonth validDate;

	// 不確定都會是default 0，個人加上not null
	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer cancelStatus;

	@ManyToOne
	@JoinColumn(name = "FK_UserId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_creditCard"), nullable = false)
	private User userId;

	public CreditCard() {
	}

	public Integer getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(Integer creditCardId) {
		this.creditCardId = creditCardId;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getCvc() {
		return cvc;
	}

	public void setCvc(Integer cvc) {
		this.cvc = cvc;
	}

	public YearMonth getValidDate() {
		return validDate;
	}

	public void setValidDate(YearMonth validDate) {
		this.validDate = validDate;
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

	public CreditCard(Integer creditCardId, Set<Order> order, Long cardNumber, Integer cvc, YearMonth validDate,
			Integer cancelStatus, User userId) {
		super();
		this.creditCardId = creditCardId;
		this.order = order;
		this.cardNumber = cardNumber;
		this.cvc = cvc;
		this.validDate = validDate;
		this.cancelStatus = cancelStatus;
		this.userId = userId;
	}

}
