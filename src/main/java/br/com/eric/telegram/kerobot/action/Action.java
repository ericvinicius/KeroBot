package br.com.eric.telegram.kerobot.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.eric.telegram.kerobot.models.Update;

public abstract class Action {

	// https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/setWebhook?url=https://telegram-kero-bot.herokuapp.com/webhook
	final String TOKEN = "480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs";
	
	private static final Logger logger = LogManager.getLogger(Action.class);

	abstract void execute(Update update);

	public void info(String tag) {
		logger.info("[" + tag + "] -> ACTION EXECUTE");
	}

}
