package br.com.eric.telegram.kerobot.controllers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.action.TelegramBot;
import br.com.eric.telegram.kerobot.models.Update;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {

	//https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/setWebhook?url=https://telegram-kero-bot.herokuapp.com/webhook
	private final String TOKEN = "480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs";
	private static final Logger logger = LogManager.getLogger(GetMessageController.class);

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage(@RequestBody Update update) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		logger.info("..................................MESSAGE..................................");
		logger.info(mapper.writeValueAsString(update));
		logger.info("...........................................................................");

		Optional.ofNullable(update.getMessage()).ifPresent(msg -> {
			TelegramBot botApi = new RestifyProxyBuilder().target(TelegramBot.class).build();
			String txt = "ola " + update.getMessage().getFrom().getFirst_name() + ", sua mensagem foi: " + update.getMessage().getText();
			botApi.send(TOKEN, update.getMessage().getChat().getId(), txt);
		});
	}

}
