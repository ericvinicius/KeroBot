package br.com.eric.telegram.kerobot.action;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eric.telegram.kerobot.models.Update;

public class ReminderAction extends Action {
	
	@Autowired
	private TelegramBot botApi;
	
	@Override
	public void execute(Update update) {
		super.info("ReminderAction");
		String txt = "ola " + update.getMessage().getFrom().getFirst_name()  + ", sua mensagem foi: " + update.getMessage().getText();
		botApi.send(TOKEN, update.getMessage().getChat().getId(), txt);
	}

}
