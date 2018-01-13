package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MessageModel {

	@Id
	private Integer messageId;

	private UserModel user;
	
	private ChatModel chat;

	private String text;

	public MessageModel(Integer message_id, UserModel userModel, ChatModel chatModel, String text) {
		this.messageId = message_id;
		userModel.addChat(chatModel);
		this.user = userModel;
		this.chat = chatModel;
		this.text = text;
	}

	public Integer getMessageId() {
		return messageId;
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

}
