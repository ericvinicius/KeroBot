package br.com.eric.telegram.kerobot.action;

import java.util.List;

import br.com.eric.telegram.kerobot.models.Update;

import java.util.Arrays;

public enum Interpreter {
	REMINDER(Arrays.asList(".*lembre em.*", ".*avise em.*", ".*avise daqui.*"), new ReminderAction());

	private List<String> pattern;
	private Action action;

	Interpreter(List<String> pattern, Action action) {
		this.pattern = pattern;
		this.action = action;
	}

	public static void validateAndExecute(Update update) {
		String goodTxt = getBetterText(update.getText().get());
		for (Interpreter interpreter : Interpreter.values()) {
			for (String pattern : interpreter.getPattern()) {
				if (goodTxt.matches(pattern)) {
					interpreter.getAction().execute(update);
				}
			}
		}
	}

	private static String getBetterText(String text) {
		return new String(text).toLowerCase();
	}

	public List<String> getPattern() {
		return pattern;
	}

	public void setPattern(List<String> pattern) {
		this.pattern = pattern;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
