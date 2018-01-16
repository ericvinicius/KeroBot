package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MessageModel {

	@Id
	private Integer updateId;

	@OneToOne
	private UserModel user;
	
	@OneToOne
	private ChatModel chat;

	private String text;
	
	private String error;
	
	public MessageModel() {
	}

	public MessageModel(Integer updateId, UserModel userModel, ChatModel chatModel, String text) {
		this.updateId = updateId;
		userModel.addChat(chatModel);
		this.user = userModel;
		this.chat = chatModel;
		this.text = text;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ChatModel getChat() {
		return chat;
	}

	public void setChat(ChatModel chat) {
		this.chat = chat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + ((updateId == null) ? 0 : updateId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		MessageModel other = (MessageModel) obj;
		if (chat == null) {
			if (other.chat != null)
				return false;
		} else if (!chat.equals(other.chat))
			return false;
		if (updateId == null) {
			if (other.updateId != null)
				return false;
		} else if (!updateId.equals(other.updateId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
