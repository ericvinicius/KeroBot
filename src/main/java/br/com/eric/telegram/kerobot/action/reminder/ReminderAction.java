package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.action.TelegramBot;
import br.com.eric.telegram.kerobot.models.Update;
import br.com.eric.telegram.kerobot.util.Unit;

@Component
public class ReminderAction extends Action {

	@Autowired
	TelegramBot botApi;

	@Autowired
	TaskScheduler taskScheduler;

	@Override
	public void execute(Update update) {
		super.info("ReminderAction");
		try {
			String txt = update.getText().get();
			String[] goodTxt = getParts(txt);
			Integer time = Integer.parseInt(goodTxt[1].replaceAll("\\D+", ""));
			String unitTxt = goodTxt[1].trim().replaceAll("\\d+", "");
			Unit unit = Unit.getFor(unitTxt).orElseThrow(
					() -> new IllegalArgumentException("Unidade de tempo nao reconhecida! [" + unitTxt + "]"));

			long milis = new Date().getTime();
			taskScheduler.schedule(doIt(update, goodTxt[0]), new Date(milis + time * unit.getTime()));

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
		return new Thread(new ReminderExecutor(update, reminder));
	}

	public class ReminderExecutor implements Runnable {
		Update update;
		String reminder;

		ReminderExecutor(Update update, String reminder) {
			this.update = update;
			this.reminder = reminder;
		}

		@Override
		public void run() {
			info("ReminderExecutor");
			String msg = "Ola " + update.getMessage().getFrom().getFirst_name() + ", lembrete: " + reminder;
			botApi.send(TOKEN, update.getMessage().getChat().getId(), msg);
		}
	}

}
