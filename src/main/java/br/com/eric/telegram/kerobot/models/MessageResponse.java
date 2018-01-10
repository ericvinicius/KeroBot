package br.com.eric.telegram.kerobot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse {
	private boolean ok;
	private Result result;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Result {
		private Integer message_id;
		private User from;
		private Chat chat;
		private Integer date;
		private String text;

		public User getFrom() {
			return from;
		}

		public void setFrom(User from) {
			this.from = from;
		}

		public Chat getChat() {
			return chat;
		}

		public void setChat(Chat chat) {
			this.chat = chat;
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
		
		public Integer getMessage_id() {
			return message_id;
		}

		public void setMessage_id(Integer message_id) {
			this.message_id = message_id;
		}
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
