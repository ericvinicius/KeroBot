package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;

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

			String msg = "Te enviarei em " + time + unit.getName();
			botApi.send(TOKEN, update.getMessage().getChat().getId(), msg);
		} catch (Exception e) {
			botApi.send(TOKEN, update.getMessage().getChat().getId(), e.getMessage());
		}
	}

	private String[] getParts(String txt) {
		String[] parts = new String[2];
		for (String token : getPatterns()) {
			if (txt.matches(token)) {
				String goodToken = getGoodToken(token);
				int tokenPos = txt.indexOf(goodToken);
				parts[0] = txt.substring(0, tokenPos);
				parts[1] = txt.substring(tokenPos, txt.length()).replaceAll(goodToken, "");
				break;
			}
		}
		return parts;
	}

	private String getGoodToken(String token) {
		return token.replaceAll("\\.|\\*|\\(|\\||\\)", "");
	}

	@Override
	public List<String> getPatterns() {
		return Arrays.asList(".*lembrar em.*", ".*avise em.*", ".*avise daqui.*", ".*lembre em.*");
	}

	public Runnable doIt(Update update, String reminder) {
		return new Thread(new ReminderExecutor(update, reminder, botApi, TOKEN));
	}

	

}
