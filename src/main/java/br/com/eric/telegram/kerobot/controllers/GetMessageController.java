package br.com.eric.telegram.kerobot.controllers;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.eric.telegram.kerobot.action.Executor;
import br.com.eric.telegram.kerobot.action.hours.HoursExecutor;
import br.com.eric.telegram.kerobot.telegram.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Controller
@RequestMapping("/webhook")
@Transactional
public class GetMessageController {

	@Autowired
	private Executor executor;

	@Autowired
	private TelegramApi telegramApi;
	
	@Autowired
	private HoursExecutor hoursExecutor;
	
	private static final Logger logger = LogManager.getLogger(GetMessageController.class);

	@RequestMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessage(@RequestBody Update update) {
		logger.info("[MESSAGE] " + update.toJson());
		try {
			executor.execute(update);
		} catch (Exception e) {
			logger.error("Error", e);
			trySendError(e);
		}
	}
	
	@RequestMapping(path="/ifttt/{username}/hour/enter")
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessageIftttEnter(@PathVariable(value = "username") String username) {
		hoursExecutor.enter(username);
	}
	
	@RequestMapping(path="/ifttt/{username}/hour/exit")
	@ResponseStatus(value = HttpStatus.OK)
	public void newMessageIftttExit(@PathVariable(value = "username") String username) {
		hoursExecutor.exit(username);
	}

	private void trySendError(Exception e) {
		try {
			telegramApi.sendMessage(TelegramApi.ADMIN_CHAT_ID, "[FALHA] - " + e.getMessage());
		} catch (Exception f) {
		}
	}
}
