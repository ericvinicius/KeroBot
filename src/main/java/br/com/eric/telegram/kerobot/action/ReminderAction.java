package br.com.eric.telegram.kerobot.action;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.models.Update;

public class ReminderAction extends Action {
	
	private TelegramBot botApi = new RestifyProxyBuilder().target(TelegramBot.class).build();
	
	@Override
	public void execute(Update update) {
		super.info("ReminderAction");
		String txt = "ola " + update.getMessage().getFrom().getFirst_name()  + ", sua mensagem foi: " + update.getMessage().getText();
		botApi.send(TOKEN, update.getMessage().getChat().getId(), txt);
	}

}
