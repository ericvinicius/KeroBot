package br.com.eric.telegram.kerobot.action.map;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.models.MessageModel;

@Component
public class MapAction extends Action {

	@Autowired
	private MapExecutor executor;

	private final List<String> PATTERNS = Arrays.asList(
			"\\/key ((?<user>@\\w+))? (?<key>\\S+) (?<value>.+)",
			"\\/key ((?<user>@\\w+))? (?<key>\\S+)",
			"\\/keys");

	@Override
	public void execute(MessageModel message, int patternPosition, Matcher matcher) {
		super.info("MapAction", "Kero has keyMap to save your thinks");

		if (patternPosition == 2) {
			super.info("MapAction", "List keys");
			executor.list(message);
		} else if (patternPosition == 1) {
			super.info("MapAction", "get key");
			executor.get(message, matcher.group("key"), matcher.group("user"));
		} else if (patternPosition == 0) {
			super.info("MapAction", "edit key");
			String value = matcher.group("value");
			executor.edit(message, matcher.group("key"), value, matcher.group("user"));
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
