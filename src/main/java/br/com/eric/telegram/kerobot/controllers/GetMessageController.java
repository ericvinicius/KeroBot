package br.com.eric.telegram.kerobot.controllers;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.action.Interpreter;
import br.com.eric.telegram.kerobot.action.UpdateRegister;
import br.com.eric.telegram.kerobot.action.reminder.ReminderAction;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {

	@Autowired
	UpdateRegister updateRegister;

	@Autowired
	ReminderAction reminderAction;

	private List<Action> actions;

	private static final Logger logger = LogManager.getLogger(GetMessageController.class);

	@PostConstruct
	public void init() {
		actions = Arrays.asList(reminderAction);
	}

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage(@RequestBody Update update) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		boolean register = updateRegister.register(update);
		if (register) {
			logger.info("[MESSAGE] " + mapper.writeValueAsString(update));
			update.getIfTextExists().ifPresent(up -> {
				Interpreter.doAction(up, actions);
			});
		} else {
			logger.info("[MESSAGE DUPLICATE] " + mapper.writeValueAsString(update));
		}
	}

}
