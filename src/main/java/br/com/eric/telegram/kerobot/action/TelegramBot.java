package br.com.eric.telegram.kerobot.action;

import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;

import br.com.eric.telegram.kerobot.models.MessageResponse;

@Path("https://api.telegram.org/")
public interface TelegramBot {

	@Path("/bot{token}/sendMessage")
	@Get
	public MessageResponse send(String token, @QueryParameter Integer chat_id, @QueryParameter String text);

}
