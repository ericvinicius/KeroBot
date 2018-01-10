package br.com.eric.telegram.kerobot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChosenInlineResult {
	private String result_id;
	private User from;
	private Location location;
	private String query;
	private String inline_message_id;

	public String getResult_id() {
		return result_id;
	}

	public void setResult_id(String result_id) {
		this.result_id = result_id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getInline_message_id() {
		return inline_message_id;
	}

	public void setInline_message_id(String inline_message_id) {
		this.inline_message_id = inline_message_id;
	}

	@Override
	public String toString() {
		return "ChosenInlineResult [result_id=" + result_id + ", from=" + from + ", location=" + location + ", query="
				+ query + ", inline_message_id=" + inline_message_id + "]";
	}

}
