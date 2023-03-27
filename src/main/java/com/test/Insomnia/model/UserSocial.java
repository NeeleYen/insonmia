package com.test.Insomnia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "UserGoogle_Table")
public class UserSocial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer socialId;

	@Nationalized
	@Column(nullable = false, length = Integer.MAX_VALUE, unique = true)
	private String socialSubId;

	@Nationalized
	@Column(nullable = false, length = Integer.MAX_VALUE, unique = true)
	private String socialName;

	@OneToOne
	@JoinColumn(name = "FK_UserId", nullable = false, foreignKey = @ForeignKey(name = "fk_user_UserGoogle"))
	private User userId;

	public UserSocial() {

	}

	public UserSocial(Integer socialId, String socialSubId, String socialName, User userId) {
		super();
		this.socialId = socialId;
		this.socialSubId = socialSubId;
		this.socialName = socialName;
		this.userId = userId;
	}

	public Integer getSocialId() {
		return socialId;
	}

	public void setSocialId(Integer socialId) {
		this.socialId = socialId;
	}

	public String getSocialSubId() {
		return socialSubId;
	}

	public void setSocialSubId(String socialSubId) {
		this.socialSubId = socialSubId;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
