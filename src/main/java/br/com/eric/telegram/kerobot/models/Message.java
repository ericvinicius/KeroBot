package br.com.eric.telegram.kerobot.models;

public class Message {
	private Integer message_id;
	private User from;
	private Integer date;
	private String text;
	private Chat chat;

	public Integer getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Integer message_id) {
		this.message_id = message_id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	@Override
	public String toString() {
		return "Message [message_id=" + message_id + ", from=" + from + ", date=" + date + ", text=" + text + ", chat="
				+ chat + "]";
	}

}
