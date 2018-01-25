package br.com.eric.telegram.kerobot.models;

public class MessageModel {
	
	private Integer messageId;

	private Integer updateId;

	private UserModel from;
	
	private ChatModel chat;

	private String text;
	
	private String error;
	
	private MessageType type;
	
	@Deprecated
	public MessageModel() {
	}

	public MessageModel(Integer messageId, Integer updateId, UserModel userModel, ChatModel chatModel, String text, MessageType type) {
		this.messageId = messageId;
		this.updateId = updateId;
		this.type = type;
		userModel.setLastChat(chatModel);
		this.from = userModel;
		this.chat = chatModel;
		this.text = text;
	}

	public UserModel getFrom() {
		return from;
	}

	public void setFrom(UserModel user) {
		this.from = user;
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
		result = prime * result + ((from == null) ? 0 : from.hashCode());
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
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
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

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

}
