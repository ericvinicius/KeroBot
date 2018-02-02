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

	private final List<String> PATTERNS = Arrays.asList("\\/key (?<key>\\w+) (?<value>\\w+)?", "\\/keys");

	@Override
	public void execute(MessageModel message, int patternPosition, Matcher matcher) {
		super.info("MapAction", "Kero has keyMap to save your thinks");

		if (patternPosition == 1) {
			executor.list(message);
		} else if (patternPosition == 0) {
			try {
				String value = matcher.group("value");
				executor.edit(message, matcher.group("key"), value);
			} catch(Exception e) {
				executor.get(message, matcher.group("key"));
			}
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
