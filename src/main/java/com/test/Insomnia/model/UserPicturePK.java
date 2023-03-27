package com.test.Insomnia.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable // 設定複合主鍵
public class UserPicturePK implements Serializable {
	// 使用者的圖片
	private static final long serialVersionUID = 1L;

	// 有時候取@JoinColumn name會當掉...
	// @JsonBackReference // 不管控
	@JsonIgnore
	@JoinColumn(name = "fk_userId", foreignKey = @ForeignKey(name = "fkc_user_pic"))
	@ManyToOne // 預設Lazy。
	private User userid;

	// 用途 BY顏
	@Column(columnDefinition = "int check(picNum in (1,2))")
	private Integer picNum;

	// 可能還要重設equals、hashCode(? 不確定

	@Override
	public int hashCode() {
		return Objects.hash(picNum, userid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPicturePK other = (UserPicturePK) obj;
		//下面的picNum是代表SQL中的資料？不太懂 BY顏
		return Objects.equals(picNum, other.picNum) && Objects.equals(userid, other.userid);
	}

	public UserPicturePK() {

	}

	public UserPicturePK(User userid, Integer picNum) {
		super();
		this.userid = userid;
		this.picNum = picNum;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public Integer getPicNum() {
		return picNum;
	}

	public void setPicNum(Integer picNum) {
		this.picNum = picNum;
	}

}
