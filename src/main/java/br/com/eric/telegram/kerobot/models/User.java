package br.com.eric.telegram.kerobot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private Integer id;
	private boolean is_bot;
	private String first_name;
	private String username;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isIs_bot() {
		return is_bot;
	}

	public void setIs_bot(boolean is_bot) {
		this.is_bot = is_bot;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", is_bot=" + is_bot + ", first_name=" + first_name + ", username=" + username + "]";
	}

}
