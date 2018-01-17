package br.com.eric.telegram.kerobot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public enum Unit {
	SECOND("segundos", 1000L, "s", "sec", "segundos?", "seconds?"),
	MINUTE("minutos", 1000L*60L, "m", "min", "minutos?", "minutes?"),
	HOUR("horas", 1000L*60L*60L, "h", "horas?", "hours?"),
	DAY("dias", 1000L*60*60*24L, "d", "dias?", "days?"),
	WEEK("semanas", 1000L*60*60*24*7L, "w", "semanas?", "weeks?");

	private List<String> names = new ArrayList<>();
	private String defaultName;
	private Long time;

	Unit(String defaultName, Long time, String... names) {
		this.defaultName = defaultName;
		this.names = Arrays.asList(names);
		this.time = time;
	}

	public static Unit getFor(String name) {
		String goodName = name.toLowerCase().trim();
		for (Unit unit : Unit.values()) {
			if (unit.match(goodName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Unidade de tempo nao reconhecida! [" + name + "]");
	}

	private boolean match(String lowerCase) {
		for (String regexName : names) {
			if (lowerCase.matches(regexName)) {
				return true;
			}
		}
		return false;
	}

	public Long getTime() {
		return time;
	}

	public List<String> getNames() {
		return names;
	}

	public Date getNextDateFor(Integer multiplier) {
		return new Date(new Date().getTime() + multiplier * this.time);
	}

	public String getDefaultName() {
		return defaultName;
	}

}
