package com.test.Insomnia.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable // 設定複合主鍵
public class ProductPicturePK implements Serializable {

	private static final long serialVersionUID = 1L;

	// 產品圖片
	// @JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_productId", foreignKey = @ForeignKey(name = "fk_product_picture"))
	private Product productId;

	// 圖片功能數字對應未定 BY顏
	private Integer picNum;

	// 可能還要重設equals、hashCode(? 不確定

	public ProductPicturePK() {

	}

	public ProductPicturePK(Product productId, Integer picNum) {
		super();
		this.productId = productId;
		this.picNum = picNum;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public Integer getPicNum() {
		return picNum;
	}

	public void setPicNum(Integer picNum) {
		this.picNum = picNum;
	}

	@Override
	public int hashCode() {
		return Objects.hash(picNum, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPicturePK other = (ProductPicturePK) obj;
		return Objects.equals(picNum, other.picNum) && Objects.equals(productId, other.productId);
	}

}
