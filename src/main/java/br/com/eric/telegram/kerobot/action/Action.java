package br.com.eric.telegram.kerobot.action;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.eric.telegram.kerobot.telegram.models.Update;

public abstract class Action {

	// https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/setWebhook?url=https://telegram-kero-bot.herokuapp.com/webhook
	public final String TOKEN = "480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs";

	private static final Logger logger = LogManager.getLogger(Action.class);

	public abstract void execute(Update update);

	public void info(String tag, String txt) {
		logger.info("[" + tag + "] -> " + txt);
	}

	public abstract List<String> getPatterns();

}
