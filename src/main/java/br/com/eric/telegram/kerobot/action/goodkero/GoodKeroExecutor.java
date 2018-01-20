package br.com.eric.telegram.kerobot.action.goodkero;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.gifhy.GiphyApi;
import br.com.eric.telegram.kerobot.gifhy.models.Response;
import br.com.eric.telegram.kerobot.telegram.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class GoodKeroExecutor {

	@Autowired
	private TelegramApi botApi;

	@Autowired
	private GiphyApi giphyApi;
	
	private static int nightGoodTime = 0;

	public void compliment(Update update) {
		Response giphy = giphyApi.random(GiphyApi.Rating.G.getName(), "thanks", GiphyApi.TOKEN);
		botApi.sendVideo(update.getMessage().getChat().getId(), giphy.getData().getImage_url());
	}

	public void thanks(Update update) {
		botApi.sendMessage(update.getMessage().getChat().getId(), "Hehe, magina");
	}

	public void goodTime(Update update, String time) {
		Calendar c = Calendar.getInstance();
		int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
		Integer chatId = update.getMessage().getChat().getId();

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
			if (nightGoodTime > 3) {
				nightGoodTime = 0;
				botApi.sendMessage(chatId, "Estou dormindo agora... ");
			} else {
				nightGoodTime++;
				String txt = "";
				for (int i = 0; i < nightGoodTime; i++) {
					txt += ":sleepy:";
				}
				botApi.sendMessage(chatId, txt);
			}
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
