package br.com.eric.telegram.kerobot.action.goodkero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.controllers.GiphyApi;
import br.com.eric.telegram.kerobot.controllers.TelegramApi;
import br.com.eric.telegram.kerobot.gifhy.models.Response;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class GoodKeroExecutor {

	@Autowired
	private TelegramApi botApi;

	@Autowired
	private GiphyApi giphyApi;

	public void compliment(Update update) {
		Response giphy = giphyApi.random(GiphyApi.Rating.G.getName(), "thanks", GiphyApi.TOKEN);
		botApi.sendVideo(update.getMessage().getChat().getId(), giphy.getData().getImage_url());
	}

	public void thanks(Update update) {
		botApi.sendMessage(update.getMessage().getChat().getId(), "Hehe, magina");
	}

	public void goodTime(Update update, String phrase) {
		Response giphy = giphyApi.random(GiphyApi.Rating.G.getName(), phrase, GiphyApi.TOKEN);
		botApi.sendVideo(update.getMessage().getChat().getId(), giphy.getData().getImage_url());
	}

}
