package br.com.eric.telegram.kerobot.controllers;

import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;

import br.com.eric.telegram.kerobot.telegram.models.MessageResponse;

@Path("https://api.telegram.org/")
public interface TelegramApi {
	
	// https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/setWebhook?url=https://telegram-kero-bot.herokuapp.com/webhook
	public final String TOKEN = "480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs";
	public final String ADMIN_ID = "@ericvcamargo";

	@Get
	@Path("/bot" + TOKEN + "/sendMessage")
	public MessageResponse sendMessage(@QueryParameter("chat_id") Integer chat_id, @QueryParameter("text") String text);
	
	@Get
	@Path("/bot" + TOKEN + "/sendMessage")
	public MessageResponse sendMessage(@QueryParameter("chat_id") String chat_id, @QueryParameter("text") String text);

	
	@Get
	@Path("/bot" + TOKEN + "/sendVideo")
	public MessageResponse sendVideo(@QueryParameter("chat_id") Integer chat_id, @QueryParameter("video") String videoURL);
	// https://api.telegram.org/bot<token>/sendVideo?chat_id=<chat_id>&video=http://i.giphy.com/13IC4LVeP5NGNi.gif
}
