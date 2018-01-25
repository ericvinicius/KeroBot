package br.com.eric.telegram.kerobot.action;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.eric.telegram.kerobot.models.MessageModel;

public abstract class Action {

	private static final Logger logger = LogManager.getLogger(Action.class);

	public abstract void execute(MessageModel message, int patternPosition, Matcher matcher);

	public void info(String tag, String txt) {
		logger.info("[" + tag + "] -> " + txt);
	}

	public abstract List<String> getPatterns();

}
