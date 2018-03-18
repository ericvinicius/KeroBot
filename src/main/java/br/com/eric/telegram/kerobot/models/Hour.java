package br.com.eric.telegram.kerobot.models;

import static br.com.eric.telegram.kerobot.util.StringUtil.fixNumberWith2Digits;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Hour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate day;

	private LocalDateTime enterHour;

	private LocalDateTime exitHour;

	@NotNull
	private Long userId;

	@Deprecated
	public Hour() {
	}

	public Hour(LocalDateTime now, LocalDate today, Long userId) {
		this.day = today;
		this.enterHour = now;
		this.userId = userId;
	}

	public Hour(LocalDate day, Long userId) {
		this.day = day;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public LocalDateTime getEnterHour() {
		return enterHour;
	}

	public void setEnter(LocalDateTime enter) {
		this.enterHour = enter;
	}

	public LocalDateTime getExitHour() {
		return exitHour;
	}

	public void setExit(LocalDateTime exit) {
		this.exitHour = exit;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String difference() {
		try {
			long hours = HOURS.between(enterHour, exitHour);
			long minutes = MINUTES.between(enterHour, exitHour);
			return hours + ":" + fixNumberWith2Digits((minutes-(60*hours)));
		} catch (Exception e) {
			return "0:00";
		}
	}

	public void enterAddMinutes(int i) {
		setEnter(enterHour.plusMinutes(i));
	}

	public void exitaddMinutes(int i) {
		setExit(exitHour.plusMinutes(i));
		
	}

	public long minutes() {
		if (enterHour == null || exitHour == null) {
			return 0;
		}
		
		return MINUTES.between(this.getEnterHour(), this.getExitHour());
	}

}
