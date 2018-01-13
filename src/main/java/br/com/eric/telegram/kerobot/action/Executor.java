package br.com.eric.telegram.kerobot.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.action.reminder.ReminderAction;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Service
public class Executor {

	@Autowired
	private ReminderAction reminderAction;

	private List<Action> textActions;

	@PostConstruct
	public void init() {
		textActions = Arrays.asList(reminderAction);
	}

	public void execute(Update update) {
		update.getIfTextExists().ifPresent(up -> doAction(up, textActions));
	}

	private String getBetterText(String text) {
		return new String(text).toLowerCase();
	}

	private void doAction(Update update, List<Action> actions) {
		String goodTxt = getBetterText(update.getText().get());
		for (Action action : actions) {
			for (String pattern : action.getPatterns()) {
				if (goodTxt.matches(pattern)) {
					action.execute(update);
					return;
				}
			}
		}
	}

}
