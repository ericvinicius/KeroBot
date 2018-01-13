package br.com.eric.telegram.kerobot.action.reminder;

import br.com.eric.telegram.kerobot.controllers.TelegramBot;
import br.com.eric.telegram.kerobot.telegram.models.Update;

public class ReminderExecutor implements Runnable {
		private Update update;
		private String reminder;
		private TelegramBot botApi;
		private String token;

		ReminderExecutor(Update update, String reminder, TelegramBot botApi, String token) {
			this.update = update;
			this.reminder = reminder;
			this.botApi = botApi;
			this.token = token;
		}

		@Override
		public void run() {
			String msg = "Ola " + update.getMessage().getFrom().getFirst_name() + ", lembrete: " + reminder;
			botApi.send(token, update.getMessage().getChat().getId(), msg);
		}
	}