package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.controllers.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;
import br.com.eric.telegram.kerobot.util.StringUtil;
import br.com.eric.telegram.kerobot.util.Unit;

@Component
public class ReminderAction extends Action {

	@Autowired
	private TelegramApi botApi;

	@Autowired
	private TaskScheduler taskScheduler;
	
	private final List<String> PATTERNS = Arrays.asList(
			"(?<start>.*)(lembr(e|ar) (em|daqui|as))(?<end>.*)",
			"(?<start>.*)(avis(e|ar) (em|daqui|as))(?<end>.*)",
			"(?<start>.*)(envi(ar|e) (em|daqui|as))(?<end>.*)"
			);

	@Override
	public void execute(Update update, int patternPosition, Matcher matcher) {
		super.info("ReminderAction", "Registering scheduler...");
		try {
			String[] txt = getParts(update.getText().get(), matcher);
			Integer time = StringUtil.getIntergers(txt[1]);
			Unit unit = Unit.getFor(StringUtil.removeNumbers(txt[1]));

			taskScheduler.schedule(doIt(update, txt[0]), unit.getNextDateFor(time));

			botApi.sendMessage(update.getMessage().getChat().getId(), "Te enviarei em " + time + " " + unit.getDefaultName());
		} catch (Exception e) {
			botApi.sendMessage(update.getMessage().getChat().getId(), e.getMessage());
		}
	}

	private String[] getParts(String txt, Matcher matcher) {
		String[] parts = new String[2];
		parts[0] = matcher.group("start");
		parts[1] = matcher.group("end");
		return parts;
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

	public Runnable doIt(Update update, String reminder) {
		return new Thread(new ReminderExecutor(update, reminder, botApi));
	}

}
