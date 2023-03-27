package com.test.Insomnia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserVerification_Table")
public class UserVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 255)
	private String verificationToken;

	@Column(columnDefinition = "datetime")
	private Date verificationMaturity;

	@OneToOne
	@JoinColumn(name = "FK_UserId", nullable = false, foreignKey = @ForeignKey(name = "fk_user_UserVerification"))
	private User userId;

	public UserVerification() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Date getVerificationMaturity() {
		return verificationMaturity;
	}

	public void setVerificationMaturity(Date verificationMaturity) {
		this.verificationMaturity = verificationMaturity;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public UserVerification(Integer id, String verificationToken, Date verificationMaturity, User userId) {
		super();
		this.id = id;
		this.verificationToken = verificationToken;
		this.verificationMaturity = verificationMaturity;
		this.userId = userId;
	}

}
