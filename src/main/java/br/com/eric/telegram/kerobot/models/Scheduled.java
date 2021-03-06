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
	private Long id;
	
	@NotNull
	private String text;
	
	private String category;
	
	@NotNull
	private Long time;
	
	private Long period;
	
	@NotNull
	private String userName;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Long chatId;
	
	private boolean frequently;
	
	@Deprecated
	public Scheduled() {
	}

	public Scheduled(String text, Date time, String category, String to, Long chatId, Long userId, Long period) {
		this.text = text;
		this.userName = to;
		this.chatId = chatId;
		this.userId = userId;
		this.period = period;
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

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public boolean isFrequently() {
		return frequently;
	}

	public void setFrequently(boolean frequently) {
		this.frequently = frequently;
	}

	public void refresh() {
		this.period = new Date().getTime() + period;
	}

}
