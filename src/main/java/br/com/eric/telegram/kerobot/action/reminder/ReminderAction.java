package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class ReminderAction extends Action {

	@Autowired
	private ReminderExecutor reminderExecutor;

	private final List<String> PATTERNS = Arrays.asList(
			"(?<start>(?s).*)( +me +)?(lembr(e|ar) +(em|daqui))(?<end>.*)",
			"(?<start>(?s).*)( +me +)?(avis(e|ar) +(em|daqui))(?<end>.*)",
			"(?<start>(?s).*)( +me +)?(envi(ar|e) +(em|daqui))(?<end>.*)",
			"/listar_lembretes");

	@Override
	public void execute(Update update, int patternPosition, Matcher matcher) {
		super.info("ReminderAction", "Registering scheduler...");
		if (patternPosition == 3) {
			reminderExecutor.listReminders(update);
		} else {
			reminderExecutor.saveReminder(update, matcher);
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
