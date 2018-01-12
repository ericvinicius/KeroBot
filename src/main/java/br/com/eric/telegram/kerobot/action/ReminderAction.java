package br.com.eric.telegram.kerobot.action;

import java.util.Arrays;
import java.util.List;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.models.Update;

public class ReminderAction extends Action {
	
	private TelegramBot botApi = new RestifyProxyBuilder().target(TelegramBot.class).build();
	
	@Override
	public void execute(Update update) {
		super.info("ReminderAction");
		
		String txt = update.getText().get();
		String txtTime = getTimePart(txt);
		
		String msg = "ola " + update.getMessage().getFrom().getFirst_name()  + ", para lembrar as: " + txtTime;
		botApi.send(TOKEN, update.getMessage().getChat().getId(), msg);
	}

	private String getTimePart(String txt) {
		for (String token : getPatterns()) {
			txt = txt.substring(txt.indexOf(token.replaceAll("\\.\\*", "")), txt.length());
		}
		return txt;
	}
	
	@Override
	public List<String> getPatterns() {
		return Arrays.asList(".*lembre em.*", ".*avise em.*", ".*avise daqui.*");
	}

}
