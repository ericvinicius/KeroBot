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
	
	public MessageResponse sendMessage(Integer chat_id, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
		return api.sendMessage(chat_id, text, inlineKeyboardMarkup);
	}

	public MessageResponse sendMessageOrEditMessage(Integer chat_id, String text, InlineKeyboardMarkup inlineKeyboardMarkup, MessageType type, Integer messageId, String callBackQueryId) {
		if (type == MessageType.MESSAGE) {
			return api.sendMessage(chat_id, text, inlineKeyboardMarkup);
		} 
		
		MessageResponse response = null;
		try {
			response = api.editMessageText(chat_id, text, messageId, inlineKeyboardMarkup);
		} catch (Exception e) {}
		api.answerCallbackQuery(callBackQueryId);
		return response;
	}
	
	public MessageResponse sendVideo(Integer chat_id, String videoURL) {
		return api.sendVideo(chat_id, videoURL);
	}

	public MessageResponse sendPhoto(Integer chat_id, String photoURL) {
		return api.sendPhoto(chat_id, photoURL);
	}

	public MessageResponse editMessage(Integer chat_id, Integer messageId, String text) {
		return api.editMessageText(chat_id, text, messageId);
	}
}
