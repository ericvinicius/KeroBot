package br.com.eric.telegram.kerobot.action.map;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.MapRepository;
import br.com.eric.telegram.kerobot.daos.UserRepository;
import br.com.eric.telegram.kerobot.models.MapModel;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class MapExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private MapRepository keyValueRepository;

	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LogManager.getLogger(MapExecutor.class);

	public void get(MessageModel message, String key, String customUsername) {
		String username = (customUsername != null && !customUsername.isEmpty()) ? customUsername : message.getFrom().getUsername();
		logger.info("Get key: " + key + " for user: " + username);
		Optional<UserModel> userOp = userRepository.findOneByUsername(username.replaceAll("@", ""));
		if (userOp.isPresent()) {
			UserModel user = userOp.get();
			Optional<MapModel> keyValueOp = keyValueRepository.findByUserIdAndChave(user.getId(), key);
			if (keyValueOp.isPresent()) {
				botApi.sendMessage(message.getChat().getId(), "Chave: " + key + "\nValor: " + keyValueOp.get().getValor());
				return;
			}
			botApi.sendMessage(message.getChat().getId(), "Usuario @" + username + " nao possui nada salvo para a chave: " + key);
			return;
		}
		botApi.sendMessage(message.getChat().getId(), "Usuario @" + username + " nao encontrado!");		
	}

	public void edit(MessageModel message, String key, String value, String customUser) {
		String username = (customUser != null && !customUser.isEmpty()) ? customUser : message.getFrom().getUsername();
		logger.info("Edit key: " + key + " for user: " + username);
		Optional<UserModel> userOp = userRepository.findOneByUsername(username.replaceAll("@", ""));
		if (userOp.isPresent()) {
			UserModel user = userOp.get();
			Optional<MapModel> keyValueOp = keyValueRepository.findByUserIdAndChave(user.getId(), key);
			if (keyValueOp.isPresent()) {
				MapModel mapModel = keyValueOp.get();
				String previous = mapModel.getValor();
				keyValueRepository.save(mapModel.setValor(value));
				botApi.sendMessage(message.getChat().getId(), "Chave: " + key + "\nValor: " + value + "\nAnterior: " + previous);
				return;
			} else {
				MapModel keyValue = new MapModel(user.getId(), key, value);
				keyValueRepository.save(keyValue);
				botApi.sendMessage(message.getChat().getId(), "Chave: " + key + "\nValor: " + value);
				return;
			}
		}
		botApi.sendMessage(message.getChat().getId(), "Usuario @" + username + " nao encontrado!");

	}

	public void list(MessageModel message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Chaves de @" + message.getFrom().getUsername() + "\n");
		keyValueRepository.findAllByUserId(message.getFrom().getId()).forEach(map -> {
			stringBuilder.append(map.getChave()).append("\n");
		});
		botApi.sendMessage(message.getChat().getId(), stringBuilder.toString());
	}

}
