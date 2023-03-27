package com.test.Insomnia.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("cardId")
	private Integer cardId;	//要處理
	
	@JsonProperty("purPlanId")
	private Integer purPlanId;
	
	@JsonProperty("intAreaId")
	private Integer intAreaId;	//要處理
	
	@JsonProperty("userId")
	private Integer userId;
	
	@JsonProperty("amount")
	private Integer amount;
	
	@JsonProperty("price")
	private Integer price;	//要處理
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("detailAddr")
	private String detailAddr;

	public OrderDTO() {
		super();
	}

	public Integer getPurPlanId() {
		return purPlanId;
	}

	public void setPurPlanId(Integer purPlanId) {
		this.purPlanId = purPlanId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getIntAreaId() {
		return intAreaId;
	}

	public void setIntAreaId(Integer intAreaId) {
		this.intAreaId = intAreaId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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
	
	
}
