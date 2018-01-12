package br.com.eric.telegram.kerobot.action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

@Configuration
public class BeanCreator {
	
	@Bean
	public TelegramBot telegramBot(){
		return new RestifyProxyBuilder().target(TelegramBot.class).build();
	}

}
