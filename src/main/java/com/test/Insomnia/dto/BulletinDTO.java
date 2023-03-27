package com.test.Insomnia.dto;

public class BulletinDTO {
	
	private String text;
	private Integer productId;


	public BulletinDTO() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BulletinDTO(String text, Integer productId) {
		super();
		this.text = text;
		this.productId = productId;
	}

	

}
