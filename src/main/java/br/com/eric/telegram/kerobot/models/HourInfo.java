package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HourInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer userId;
	
	private Integer hour = 8 * 60;
	
	private Integer lunchTime = 45;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(Integer lunchTime) {
		this.lunchTime = lunchTime;
	}

	public Long getExtraFor(Hour hour) {
		long between = hour.minutes();
		return between == 0L ? 0L : (between - (lunchTime + this.hour));
	}
	
}
