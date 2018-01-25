package br.com.eric.telegram.kerobot.models;

public class InlineKeyboardButton {

	private String text;
	private String callback_data;

	public InlineKeyboardButton(String text, String callback_data) {
		this.text = text;
		this.setCallback_data(callback_data);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCallback_data() {
		return callback_data;
	}

	public void setCallback_data(String callback_data) {
		this.callback_data = callback_data;
	}

}
