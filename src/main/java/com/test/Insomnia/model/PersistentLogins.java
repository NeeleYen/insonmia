package com.test.Insomnia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {
	@Id
	@Column(nullable = false, length = 64)
	private String series;

	@Column(nullable = false, length = 64)
	private String username;

	@Column(nullable = false, length = 64)
	private String token;

	@Column(columnDefinition = "datetime", nullable = false)
	private Date last_used;

	public PersistentLogins() {
	}

	public PersistentLogins(String series, String username, String token, Date last_used) {
		super();
		this.series = series;
		this.username = username;
		this.token = token;
		this.last_used = last_used;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLast_used() {
		return last_used;
	}

	public void setLast_used(Date last_used) {
		this.last_used = last_used;
	}

}
