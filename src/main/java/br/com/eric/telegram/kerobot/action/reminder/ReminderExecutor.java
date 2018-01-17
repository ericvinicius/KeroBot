package br.com.eric.telegram.kerobot.action.reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.controllers.TelegramApi;
import br.com.eric.telegram.kerobot.models.Scheduled;

@Service
public class ReminderExecutor {
	
	@Autowired
	private TelegramApi botApi;

	public void execute(Scheduled s) {
		String msg = "@" + s.getUserName() + ", lembrete: " + s.getText();
		botApi.sendMessage(s.getChatId(), msg);
	}
}