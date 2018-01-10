package br.com.eric.telegram.kerobot.controllers;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.eric.telegram.kerobot.action.Sender;
import br.com.eric.telegram.kerobot.models.Update;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {
	
	private final String TOKEN = "530257705:AAEA0JYLsFlrI0gUKEeq83sOuO1OQQLvkSo";
	private static final Logger logger = LogManager.getLogger(GetMessageController.class);

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage(Update update) {
		logger.info("..................................MESSAGE..................................");
		Sender.botApi.send(TOKEN, update.getMessage().getChat().getId(), "ola " + update.getMessage().getFrom().getFirst_name() + ", sua mensagem foi: " + update.getMessage().getText());
	}

}
