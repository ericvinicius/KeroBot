package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.controllers.TelegramBot;
import br.com.eric.telegram.kerobot.telegram.models.Update;
import br.com.eric.telegram.kerobot.util.StringUtil;
import br.com.eric.telegram.kerobot.util.Unit;

@Component
public class ReminderAction extends Action {

	@Autowired
	private TelegramBot botApi;

	@Autowired
	private TaskScheduler taskScheduler;

	@Override
	public void execute(Update update) {
		super.info("ReminderAction", "Registering scheduler...");
		try {
			String[] txt = getParts(update.getText().get());
			Integer time = StringUtil.getIntergers(txt[1]);
			Unit unit = Unit.getFor(StringUtil.removeNumbers(txt[1]));

			taskScheduler.schedule(doIt(update, txt[0]), unit.getNextDateFor(time));

			botApi.send(TOKEN, update.getMessage().getChat().getId(), "Te enviarei em " + time + " " + unit.getDefaultName());
		} catch (Exception e) {
			botApi.send(TOKEN, update.getMessage().getChat().getId(), e.getMessage());
		}
	}

	private String[] getParts(String txt) {
		String[] parts = new String[2];
		for (String regex : getPatterns()) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(txt);
			if (m.find()) {
				parts[0] = m.group("start");
				parts[1] = m.group("end");
				break;
			}
		}
		return parts;
	}

	@Override
	public List<String> getPatterns() {
		return Arrays.asList(
				"(?<start>.*)(lembr(e|ar) (em|daqui|as))(?<end>.*)",
				"(?<start>.*)(avis(e|ar) (em|daqui|as))(?<end>.*)",
				"(?<start>.*)(envi(ar|e) (em|daqui|as))(?<end>.*)"
				);
	}

	public Runnable doIt(Update update, String reminder) {
		return new Thread(new ReminderExecutor(update, reminder, botApi, TOKEN));
	}

}
