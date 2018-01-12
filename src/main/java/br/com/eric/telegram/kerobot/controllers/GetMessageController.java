package br.com.eric.telegram.kerobot.controllers;

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

import br.com.eric.telegram.kerobot.action.Interpreter;
import br.com.eric.telegram.kerobot.models.Update;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {
	
	private static final Logger logger = LogManager.getLogger(GetMessageController.class);

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage(@RequestBody Update update) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		logger.info("..................................MESSAGE..................................");
		logger.info(mapper.writeValueAsString(update));
		logger.info("...........................................................................");

		update.getIfTextExists().ifPresent(Interpreter::validateAndExecute);
	}

}
