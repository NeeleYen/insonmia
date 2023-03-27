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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserResetPassword_Table")
public class UserResetPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 255)
	private String resetPasswordToken;

	@Column(columnDefinition = "datetime")
	private Date maturity;

	@OneToOne
	@JoinColumn(name = "FK_UserId", nullable = false, foreignKey = @ForeignKey(name = "fk_user_ResetPassword"))
	private User userId;

	public UserResetPassword() {
	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public UserResetPassword(Integer id, String resetPasswordToken, Date maturity, User userId) {
		super();
		this.id = id;
		this.resetPasswordToken = resetPasswordToken;
		this.maturity = maturity;
		this.userId = userId;
	}

}
