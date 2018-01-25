package br.com.eric.telegram.kerobot.action.goodkero;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.gifhy.GiphyApi;
import br.com.eric.telegram.kerobot.gifhy.models.Response;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class GoodKeroExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private GiphyApi giphyApi;
	
	private static int NIGHT_TRIES = 0;

	public void compliment(MessageModel message) {
		Response giphy = giphyApi.random(GiphyApi.Rating.G.getName(), "thanks", GiphyApi.TOKEN);
		botApi.sendVideo(message.getChat().getId(), giphy.getData().getImage_url());
	}

	public void thanks(MessageModel message) {
		botApi.sendMessage(message.getChat().getId(), "Hehe, magina");
	}

	public void goodTime(MessageModel message, String phrase, String time) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
		Integer chatId = message.getChat().getId();

		if (time.equals("dia") && timeOfDay >= 6 && timeOfDay < 12) {
			doIt(chatId, time);
			return;
		} else if (time.equals("tarde") && timeOfDay >= 12 && timeOfDay < 18) {
			doIt(chatId, time);
			return;
		} else if (time.equals("noite") && timeOfDay >= 18 && timeOfDay < 24) {
			doIt(chatId, time);
			return;
		} else if (timeOfDay == 24 || timeOfDay < 6) {
			if (NIGHT_TRIES >= 3) {
				NIGHT_TRIES = 0;
				botApi.sendMessage(chatId, "Estou dormindo agora... ");
			} else {
				NIGHT_TRIES++;
				String txt = "";
				for (int i = 0; i < NIGHT_TRIES; i++) {
					txt += ":sleepy:";
				}
				botApi.sendMessage(chatId, txt);
			}
			return;
		}
		failDoIt(chatId, time);

	}

	private void failDoIt(Integer chatId, String time) {
		botApi.sendMessage(chatId, "Mas nem esta de " + time + "... Acho que esta na hora de comer doces...");
	}

	public void doIt(Integer chatId, String time) {
		Response giphy = giphyApi.random(GiphyApi.Rating.G.getName(), time, GiphyApi.TOKEN);
		botApi.sendVideo(chatId, giphy.getData().getImage_url());
	}

}
