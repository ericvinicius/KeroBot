package br.com.eric.telegram.kerobot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.controllers.TelegramBot;

@Configuration
public class AppConfiguration {
	@Bean
	TaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}
	
	@Bean
	TelegramBot telegram(){
		return new RestifyProxyBuilder().target(TelegramBot.class).build();
	}
}
