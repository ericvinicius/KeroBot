package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.models.MessageModel;

@Component
public class ReminderAction extends Action {

	@Autowired
	private ReminderExecutor reminderExecutor;

	private final List<String> PATTERNS = Arrays.asList(
			"(?<start>(?s).*)( +me +)?(lembr(e|ar) +(em|daqui))(?<end>.*)",
			"(?<start>(?s).*)( +me +)?(avis(e|ar) +(em|daqui))(?<end>.*)",
			"(?<start>(?s).*)( +me +)?(envi(ar|e) +(em|daqui))(?<end>.*)",
			"/listar_lembretes",
			"/snooze_reminder_(?<end>.*)",
			"/frequently_reminder_(?<end>\\d+)",
			"/frequently_reminder_cancel_(?<end>\\d+)",
			"/reminder_delete_(?<end>\\d+)");

	@Override
	public void execute(MessageModel message, int patternPosition, Matcher matcher) {
		super.info("ReminderAction", "Registering scheduler...");
		if (patternPosition == 3) {
			reminderExecutor.listReminders(message);
		} else if (patternPosition == 4) { 
			reminderExecutor.snooze(message, matcher);
		} else if (patternPosition == 5) {
			reminderExecutor.frequently(message, matcher);
		} else if (patternPosition == 6) {
			reminderExecutor.frequentlyCancel(message, matcher);
		} else if (patternPosition == 7) {
			reminderExecutor.cancelReminder(message, matcher);
		} else {
			reminderExecutor.saveReminder(message, matcher);
		}
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
