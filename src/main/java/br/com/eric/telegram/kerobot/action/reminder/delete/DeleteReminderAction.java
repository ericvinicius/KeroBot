package br.com.eric.telegram.kerobot.action.reminder.delete;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.models.MessageModel;

@Component
public class DeleteReminderAction extends Action {

	@Autowired
	private DeleteReminderExecutor deleteReminderExecutor;

	private final List<String> PATTERNS = Arrays.asList("(kero+|@?cutekerobot),? deletar ultimo lembrete.*");

	@Override
	public void execute(MessageModel update, int patternPosition, Matcher matcher) {
		Integer chatId = update.getChat().getId();
		Integer userId = update.getUser().getId();
		deleteReminderExecutor.deleteLast(chatId, userId);
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
