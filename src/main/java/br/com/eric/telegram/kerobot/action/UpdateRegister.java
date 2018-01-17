package br.com.eric.telegram.kerobot.action;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.daos.ChatRepository;
import br.com.eric.telegram.kerobot.daos.UserRepository;
import br.com.eric.telegram.kerobot.models.ChatModel;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.models.Chat;
import br.com.eric.telegram.kerobot.telegram.models.Update;
import br.com.eric.telegram.kerobot.telegram.models.User;

@Service
public class UpdateRegister {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRepository chatRepository;

	public Optional<MessageModel> validateMessage(Update update) {
		return Optional.ofNullable(update.getMessage()).map(message -> {
			User from = message.getFrom();
			Chat chat = message.getChat();

			if (from != null && chat != null) {
				ChatModel chatModel = new ChatModel(chat.getId(), chat.getTitle(), chat.getType());
				UserModel userModel = new UserModel(from.getId(), from.getFirst_name(), from.getUsername(), chatModel,
						from.isIs_bot());
				return new MessageModel(update.getUpdate_id(), userModel, chatModel, message.getText());
			}
			return null;
		});
	}

	public void register(MessageModel message) {
		chatRepository.save(message.getChat());
		userRepository.save(message.getUser());
		// messageRepository.save(message);
	}

}
