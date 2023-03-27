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

@Entity
@Table(name = "Chat_Table")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chatId;

	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String content;

	@ManyToOne
	@JoinColumn(name = "fK_UserId_sender", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_sender"), nullable = false)
	private User userIdSender;

	@ManyToOne
	@JoinColumn(name = "fK_UserId_receiver", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_receiver"), nullable = false)
	private User userIdReceiver;

	// 時間部分不確定
	@Temporal(TemporalType.TIMESTAMP) // 如果用SQL date 就不用寫這個，但就不知道這是什麼
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 只能在java環境做設定，大小寫嚴格不同意義
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8") // 轉乘JSON才吃的到
	@Column(columnDefinition = "datetime", nullable = false)
	private Date time;

	// 發出訊息自動生成時間
	@PrePersist
	public void onCreate() {
		if (time == null) {
			time = new Date();
		}
	}
	
	public Chat() {
	}

	public Chat(Integer chatId, String content, User userIdSender, User userIdReceiver, Date time) {
		super();
		this.chatId = chatId;
		this.content = content;
		this.userIdSender = userIdSender;
		this.userIdReceiver = userIdReceiver;
		this.time = time;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUserIdSender() {
		return userIdSender;
	}

	public void setUserIdSender(User userIdSender) {
		this.userIdSender = userIdSender;
	}

	public User getUserIdReceiver() {
		return userIdReceiver;
	}

	public void setUserIdReceiver(User userIdReceiver) {
		this.userIdReceiver = userIdReceiver;
	}

}
