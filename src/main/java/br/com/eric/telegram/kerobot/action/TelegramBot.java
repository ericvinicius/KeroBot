package br.com.eric.telegram.kerobot.action;

import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.PathParameter;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;

import br.com.eric.telegram.kerobot.models.MessageResponse;

@Path("https://api.telegram.org/")
public interface TelegramBot {

	@Path("/bot{token}/sendMessage")
	@Get
	public MessageResponse send(@PathParameter("token") String token, @QueryParameter("chat_id") Integer chat_id, @QueryParameter("text") String text);

}
