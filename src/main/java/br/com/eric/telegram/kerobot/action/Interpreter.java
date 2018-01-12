package br.com.eric.telegram.kerobot.action;

import br.com.eric.telegram.kerobot.action.reminder.ReminderAction;
import br.com.eric.telegram.kerobot.models.Update;

public enum Interpreter {
	REMINDER(new ReminderAction());

	private Action action;

	Interpreter(Action action) {
		this.action = action;
	}

	public static void validateAndExecute(Update update) {
		String goodTxt = getBetterText(update.getText().get());
		for (Interpreter interpreter : Interpreter.values()) {
			for (String pattern : interpreter.getAction().getPatterns()) {
				if (goodTxt.matches(pattern)) {
					interpreter.getAction().execute(update);
				}
			}
		}
	}

	private static String getBetterText(String text) {
		return new String(text).toLowerCase();
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
