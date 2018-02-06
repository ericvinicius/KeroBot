package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserModel {

	@Id
	private Integer id;

	@OneToOne
	private ChatModel lastChat;

	private Boolean bot;

	private String fisrtName;

	private String username;

	public UserModel() {
	}

	public UserModel(Integer id, String first_name, String username, ChatModel chatModel, boolean is_bot) {
		this.id = id;
		this.fisrtName = first_name;
		this.username = username;
		this.lastChat = chatModel;
		this.bot = is_bot;
	}

	public Integer getId() {
		return id;
	}

	public ChatModel getLastChat() {
		return lastChat;
	}

	public void setLastChat(ChatModel chats) {
		this.lastChat = chats;
	}

	public Boolean getBot() {
		return bot;
	}

	public void setBot(Boolean bot) {
		this.bot = bot;
	}

	public String getFisrtName() {
		return fisrtName;
	}

	public void setFisrtName(String fisrtName) {
		this.fisrtName = fisrtName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bot == null) ? 0 : bot.hashCode());
		result = prime * result + ((lastChat == null) ? 0 : lastChat.hashCode());
		result = prime * result + ((fisrtName == null) ? 0 : fisrtName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (bot == null) {
			if (other.bot != null)
				return false;
		} else if (!bot.equals(other.bot))
			return false;
		if (lastChat == null) {
			if (other.lastChat != null)
				return false;
		} else if (!lastChat.equals(other.lastChat))
			return false;
		if (fisrtName == null) {
			if (other.fisrtName != null)
				return false;
		} else if (!fisrtName.equals(other.fisrtName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
