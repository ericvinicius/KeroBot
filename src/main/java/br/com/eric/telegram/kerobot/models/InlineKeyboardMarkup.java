package br.com.eric.telegram.kerobot.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InlineKeyboardMarkup {
	
	private InlineKeyboardButton[][] inline_keyboard = new InlineKeyboardButton[3][3];

	public InlineKeyboardMarkup(InlineKeyboardButton[][] keyboard) {
		inline_keyboard = keyboard;
	}

	public InlineKeyboardButton[][] getInline_keyboard() {
		return inline_keyboard;
	}

	public void setInline_keyboard(InlineKeyboardButton[][] inline_keyboard) {
		this.inline_keyboard = inline_keyboard;
	}
	
	@Override
	public String toString() {
		return toJson();
	}
	
	public String toJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return toString();
	}

}
