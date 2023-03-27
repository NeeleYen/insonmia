package com.test.Insomnia.model;

import java.util.Date;

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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Bulletin_Table")	//留言板
public class Bulletin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bulletinId;

	@Nationalized
	@Column(length = Integer.MAX_VALUE, nullable = false)
	private String content;

	//留言時間
	// 時間部分不確定
	@Temporal(TemporalType.TIMESTAMP) // 如果用SQL date 就不用寫這個，但就不知道這是什麼
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 只能在java環境做設定，大小寫嚴格不同意義
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8") // 轉乘JSON才吃的到
	@Column(columnDefinition = "datetime", nullable = false)
	private Date addedTime;

	//編輯時間
	// 時間部分不確定
	@Temporal(TemporalType.TIMESTAMP) // 如果用SQL date 就不用寫這個，但就不知道這是什麼
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 只能在java環境做設定，大小寫嚴格不同意義
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8") // 轉乘JSON才吃的到
	@Column(columnDefinition = "datetime", nullable = false)
	private Date modifyTime;

	// 多(留言)對一(產品)雙向
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "fk_ProductId", nullable = false, foreignKey = @ForeignKey(name = "fkc_Bulletin_Product"))
	private Product product;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FK_UserId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_bulletin"), nullable = false)
	private User userId;

	// 留言時自動生成時間
	@PrePersist
	public void onCreate() {
		if (addedTime == null) {
			addedTime = new Date();
		}
	}
	
	public Bulletin() {
	}

	public Bulletin(Integer bulletinId, String content, Date addedTime, Date modifyTime, Product product, User userId) {
		super();
		this.bulletinId = bulletinId;
		this.content = content;
		this.addedTime = addedTime;
		this.modifyTime = modifyTime;
		this.product = product;
		this.userId = userId;
	}

	public Integer getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Integer bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
