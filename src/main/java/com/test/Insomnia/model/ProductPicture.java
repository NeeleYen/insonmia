package com.test.Insomnia.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ProductPicture_Table")
public class ProductPicture {

	@EmbeddedId // 複合主鍵
	private ProductPicturePK picId;

	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String filePath;

	public ProductPicture() {
	}
	
	public ProductPicture(ProductPicturePK picId, String filePath) {
		super();
		this.picId = picId;
		this.filePath = filePath;
	}

	public ProductPicturePK getPicId() {
		return picId;
	}

	public void setPicId(ProductPicturePK picId) {
		this.picId = picId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
