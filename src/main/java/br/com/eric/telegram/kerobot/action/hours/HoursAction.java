package br.com.eric.telegram.kerobot.action.hours;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.models.MessageModel;

@Component
public class HoursAction extends Action {

	@Autowired
	private HoursExecutor hoursExecutor;

	private final List<String> PATTERNS = Arrays.asList(
			"\\/ponto_entrada",
			"\\/ponto_saida", 
			"\\/(listar|ver|consultar)_horas",
			"\\/ponto_refresh",
			"\\/ponto_(?<hour>entrada|saida) (?<action>\\+|-)",
			"\\/ponto_(?<hour>entrada|saida) (?<action>\\d\\d/\\d\\d/\\d\\d\\d\\d \\d\\d:\\d\\d)"
			);

	@Override
	public void execute(MessageModel message, int patternPosition, Matcher matcher) {
		super.info("HoursRegister", "kero controll hours to you");

		switch (patternPosition) {
		case 0:
			super.info("HoursRegister", "ENTER");
			hoursExecutor.enter(message);
			break;
		case 1:
			super.info("HoursRegister", "EXIT");
			hoursExecutor.exit(message);
			break;
		case 2:
			super.info("HoursRegister", "LIST");
			hoursExecutor.list(message);
			break;
		case 3:
			super.info("HoursRegister", "REFRESH");
			hoursExecutor.refresh(message);
			break;
		case 4:
			super.info("HoursRegister", "EDIT");
			hoursExecutor.edit(message, matcher);
			break;
		case 5:
			super.info("HoursRegister", "EDIT");
			hoursExecutor.editEspecific(message, matcher);
			break;
			
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
