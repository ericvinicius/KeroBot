package br.com.eric.telegram.kerobot.action;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.daos.ChatRepository;
import br.com.eric.telegram.kerobot.daos.HourInfoRepository;
import br.com.eric.telegram.kerobot.daos.UserRepository;
import br.com.eric.telegram.kerobot.models.ChatModel;
import br.com.eric.telegram.kerobot.models.HourInfo;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.MessageType;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.models.Chat;
import br.com.eric.telegram.kerobot.telegram.models.Message;
import br.com.eric.telegram.kerobot.telegram.models.Update;
import br.com.eric.telegram.kerobot.telegram.models.User;

@Service
public class UpdateRegister {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private HourInfoRepository hourInfoRepository;

	public Optional<MessageModel> validateMessage(Update update) {
		MessageModel messageModel = Optional.ofNullable(update.getMessage()).map(message -> {
			User from = message.getFrom();
			Chat chat = message.getChat();

			if (from != null && chat != null && message.getText() != null && !message.getText().isEmpty()) {
				ChatModel chatModel = new ChatModel(chat.getId(), chat.getTitle(), chat.getType());
				UserModel userModel = new UserModel(from.getId(), from.getFirst_name(), from.getUsername(), chatModel,
						from.isIs_bot());
				return new MessageModel(message.getMessage_id(), update.getUpdate_id(), userModel, chatModel, message.getText(), MessageType.MESSAGE);
			}
			return null;
		}).orElse(null);
		
		MessageModel messageModelFromQuery = Optional.ofNullable(update.getCallback_query()).map(query -> {
			User from = query.getFrom();
			Message message = query.getMessage();

			if (from != null && message != null && query.getData() != null && !query.getData().isEmpty()) {
				Chat chat = message.getChat();
				if (chat != null) {
					
					ChatModel chatModel = new ChatModel(chat.getId(), chat.getTitle(), chat.getType());
					UserModel userModel = new UserModel(from.getId(), from.getFirst_name(), from.getUsername(), chatModel,
							from.isIs_bot());
					MessageType callbackQuery = MessageType.CALLBACK_QUERY;
					callbackQuery.put("callback_query_id", query.getId());
					callbackQuery.put("message_text", message.getText());
					return new MessageModel(message.getMessage_id(), update.getUpdate_id(), userModel, chatModel, query.getData(), callbackQuery);
				}
			}
			return null;
		}).orElse(null);
		
		return getFirstNonNull(messageModel, messageModelFromQuery);
	}
	
	public Optional<MessageModel> getFirstNonNull(MessageModel...messageModels) {
		for (MessageModel messageModel : messageModels) {
			if (messageModel != null) {
				return Optional.of(messageModel);
			}
		}
		return Optional.empty();
	}

	public void register(MessageModel message) {
		chatRepository.save(message.getChat());
		UserModel userModel = userRepository.save(message.getFrom());
		HourInfo hour = hourInfoRepository.findOneByUserId(userModel.getId());
		if (hour == null) {
			hour = new HourInfo();
			hour.setUserId(userModel.getId());
			hourInfoRepository.save(hour);
		}
	}

}
