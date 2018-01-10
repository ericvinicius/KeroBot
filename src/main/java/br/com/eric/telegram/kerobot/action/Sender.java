package br.com.eric.telegram.kerobot.action;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

public class Sender {

	public static final TelegramBot botApi = new RestifyProxyBuilder().target(TelegramBot.class).build();
}
