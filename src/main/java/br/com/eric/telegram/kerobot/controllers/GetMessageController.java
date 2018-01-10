package br.com.eric.telegram.kerobot.controllers;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.eric.telegram.kerobot.Boot;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {
	
	private final String TOKEN = "530257705:AAEA0JYLsFlrI0gUKEeq83sOuO1OQQLvkSo";
	private static final Logger logger = LogManager.getLogger(Boot.class);

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage() {
		logger.info("..................................MESSAGE..................................");
	}

}
