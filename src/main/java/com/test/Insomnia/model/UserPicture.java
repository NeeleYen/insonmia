package com.test.Insomnia.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserPicture_Table")
public class UserPicture {

	@EmbeddedId // 複合主鍵，連到Pk
	private UserPicturePK userPicturePk;

	@Lob
	@JsonIgnore
	private byte[] picture; // 圖片檔

	public UserPicture() {
		super();
	}
	
	public UserPicture(UserPicturePK userPicturePk, byte[] picture) {
		super();
		this.userPicturePk = userPicturePk;
		this.picture = picture;
	}

	public UserPicturePK getUserPicturePk() {
		return userPicturePk;
	}

	public void setUserPicturePk(UserPicturePK userPicturePk) {
		this.userPicturePk = userPicturePk;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
