package br.com.eric.telegram.kerobot.util;

import java.util.Optional;

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
	
	public static Optional<Unit> getFor(String name){
		for (Unit unit : Unit.values()) {
			if (unit.name.equals(name.toLowerCase())) {
				return Optional.ofNullable(unit);
			}
		}
		return Optional.empty();
	}

	public Long getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

}
