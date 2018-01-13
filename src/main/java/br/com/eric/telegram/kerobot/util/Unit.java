package br.com.eric.telegram.kerobot.util;

import java.util.Date;

public enum Unit {
	SECOND("s", 1000L),
	MINUTE("m", 1000L * 60L),
	HOUR("h", 1000L * 60L * 60L),
	DAY("d", 1000L * 60 * 60 * 24L),
	WEEK("w", 1000L * 60 * 60 * 24 * 7L);

	private String name;
	private Long time;

	Unit(String name, Long time) {
		this.name = name;
		this.time = time;
	}

	public static Unit getFor(String name) {
		for (Unit unit : Unit.values()) {
			if (unit.name.equals(name.toLowerCase())) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Unidade de tempo nao reconhecida! [" + name + "]");
	}

	public Long getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	public Date getNextDateFor(Integer multiplier) {
		return new Date(new Date().getTime() + multiplier * this.time);
	}

}
