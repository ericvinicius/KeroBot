package br.com.eric.telegram.kerobot.models;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
	CALLBACK_QUERY(new HashMap<>()),
	MESSAGE(new HashMap<>());
	
	private Map<String, Object> atributes;

	private MessageType(Map<String, Object> atributes) {
		this.atributes = atributes;
	}
	
	public String getString(String key) {
		return (String) atributes.get(key);
	}
	
	public void put(String key, Object value) {
		atributes.put(key, value);
	}
}
