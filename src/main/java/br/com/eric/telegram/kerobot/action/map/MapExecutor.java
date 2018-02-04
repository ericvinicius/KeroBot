package br.com.eric.telegram.kerobot.action.map;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.MapRepository;
import br.com.eric.telegram.kerobot.models.MapModel;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class MapExecutor {

	@Autowired
	private TelegramApiExecutor botApi;
	
	@Autowired
	private MapRepository keyValueRepository;

	public void get(MessageModel message, String key) {
		Optional<MapModel> keyValueOp = keyValueRepository.findByUserIdAndChave(message.getFrom().getId(), key);
		if (keyValueOp.isPresent()) {
			botApi.sendMessage(message.getChat().getId(), "Chave: " + key + "\nValor: " + keyValueOp.get().getValor());
		} else {
			botApi.sendMessage(message.getChat().getId(), "Voce nao possui nada salvo para a chave: " + key);
		}
	}

	public void edit(MessageModel message, String key, String value) {
		Optional<MapModel> keyValueOp = keyValueRepository.findByUserIdAndChave(message.getFrom().getId(), key);
		if (keyValueOp.isPresent()) {
			keyValueRepository.save(keyValueOp.get().setValor(value));
		} else {
			MapModel keyValue = new MapModel(message.getFrom().getId(), key, value);
			keyValueRepository.save(keyValue);
		}
		botApi.sendMessage(message.getChat().getId(), "Chave: " + key + "\nValor: " + value);
	}

	public void list(MessageModel message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Chaves de @" + message.getFrom().getUsername());
		keyValueRepository.findAllByUserId(message.getFrom().getId()).forEach(map -> {
			stringBuilder.append(map.getChave()).append("\n");
		});
		botApi.sendMessage(message.getChat().getId(), stringBuilder.toString());
	}

}
