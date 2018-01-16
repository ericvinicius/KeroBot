package br.com.eric.telegram.kerobot.action;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.action.goodkero.GoodKeroAction;
import br.com.eric.telegram.kerobot.action.reminder.ReminderAction;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Service
public class Executor {

	@Autowired
	private ReminderAction reminderAction;

	@Autowired
	private GoodKeroAction goodKeroAction;

	private List<Action> textActions;

	@PostConstruct
	public void init() {
		textActions = Arrays.asList(reminderAction, goodKeroAction);
	}

	public Boolean execute(Update update) {
		return update.getIfTextExists().map(up -> {
			return doAction(up, textActions);
		}).orElse(false);
	}

	private String getBetterText(String text) {
		return new String(text).toLowerCase();
	}

	private Boolean doAction(Update update, List<Action> actions) {
		String goodTxt = getBetterText(update.getText().get());
		for (Action action : actions) {
			List<String> patterns = action.getPatterns();
			for (int i = 0; i < patterns.size(); i++) {
				String pattern = patterns.get(i);
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(goodTxt);
				if (m.find()) {
					action.execute(update, i, m);
					return true;
				}
			}
		}
		return false;
	}

}
