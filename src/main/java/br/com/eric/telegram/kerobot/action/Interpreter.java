package br.com.eric.telegram.kerobot.action;

import java.util.List;

import br.com.eric.telegram.kerobot.telegram.models.Update;

public class Interpreter {

	private static String getBetterText(String text) {
		return new String(text).toLowerCase();
	}

	public static void doAction(Update update, List<Action> actions) {
		String goodTxt = getBetterText(update.getText().get());
		for (Action action : actions) {
			for (String pattern : action.getPatterns()) {
				if (goodTxt.matches(pattern)) {
					action.execute(update);
				}
			}
		}
	}

}
