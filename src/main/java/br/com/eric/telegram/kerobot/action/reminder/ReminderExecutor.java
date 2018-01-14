package br.com.eric.telegram.kerobot.action.reminder;

import br.com.eric.telegram.kerobot.controllers.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;

public class ReminderExecutor implements Runnable {
		private Update update;
		private String reminder;
		private TelegramApi botApi;

		ReminderExecutor(Update update, String reminder, TelegramApi botApi) {
			this.update = update;
			this.reminder = reminder;
			this.botApi = botApi;
		}

		@Override
		public void run() {
			String msg = "@" + update.getMessage().getFrom().getUsername() + ", lembrete: " + reminder;
			botApi.sendMessage(update.getMessage().getChat().getId(), msg);
		}
	}