package br.com.eric.telegram.kerobot.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Scheduled {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String text;
	
	private String category;
	
	@NotNull
	private Long time;
	
	@NotNull
	private String userName;
	
	@NotNull
	private Integer userId;
	
	@NotNull
	private Integer chatId;
	
	@Deprecated
	public Scheduled() {
	}

	public Scheduled(String text, Date time, String category, String to, Integer chatId, Integer userId) {
		this.text = text;
		this.userName = to;
		this.chatId = chatId;
		this.userId = userId;
		this.time = time.getTime();
		this.category = category;
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUserName() {
		return userName;
	}

	public void setTo(String to) {
		this.userName = to;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
