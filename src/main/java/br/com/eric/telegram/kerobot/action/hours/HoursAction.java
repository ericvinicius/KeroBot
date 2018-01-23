package br.com.eric.telegram.kerobot.action.hours;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class HoursAction extends Action {

	@Autowired
	private HoursExecutor hoursExecutor;

	private final List<String> PATTERNS = Arrays.asList("(?<username>@\\w+ )?\\/ponto_entrada",
			"(?<username>@\\w+ )?\\/ponto_saida", "\\/(listar|ver|consultar)_horas");

	@Override
	public void execute(Update update, int patternPosition, Matcher matcher) {
		super.info("HoursRegister", "kero controll hours to you");

		switch (patternPosition) {
		case 0:
			super.info("HoursRegister", "ENTER");
			hoursExecutor.enter(update, matcher.group("username"));
			break;
		case 1:
			super.info("HoursRegister", "EXIT");
			hoursExecutor.exit(update, matcher.group("username"));
			break;
		case 2:
			super.info("HoursRegister", "LIST");
			hoursExecutor.list(update);
			break;
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
