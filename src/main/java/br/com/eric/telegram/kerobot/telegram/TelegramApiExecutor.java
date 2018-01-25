package br.com.eric.telegram.kerobot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.models.InlineKeyboardMarkup;
import br.com.eric.telegram.kerobot.models.MessageType;
import br.com.eric.telegram.kerobot.telegram.models.MessageResponse;

@Component
public class TelegramApiExecutor {

	@Autowired
	private TelegramApi api;

	public MessageResponse sendMessage(Integer chat_id, String text) {
		return api.sendMessage(chat_id, text);
	}

	public MessageResponse sendMessageOrEditMessage(Integer chat_id, String text, InlineKeyboardMarkup inlineKeyboardMarkup, MessageType type, Integer messageId) {
		if (type == MessageType.MESSAGE) {
			return api.sendMessage(chat_id, text, inlineKeyboardMarkup);
		} 
		
		return api.editMessageText(chat_id, text, messageId, inlineKeyboardMarkup);
	}

	public MessageResponse sendVideo(Integer chat_id, String videoURL) {
		return api.sendVideo(chat_id, videoURL);
	}

	public MessageResponse sendPhoto(Integer chat_id, String photoURL) {
		return api.sendPhoto(chat_id, photoURL);
	}
}
