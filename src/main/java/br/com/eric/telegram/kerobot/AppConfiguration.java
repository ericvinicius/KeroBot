package br.com.eric.telegram.kerobot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.controllers.GiphyApi;
import br.com.eric.telegram.kerobot.controllers.TelegramApi;

@Configuration
public class AppConfiguration {
	@Bean
	TaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}
	
	@Bean
	TelegramApi telegram(){
		return new RestifyProxyBuilder().target(TelegramApi.class).build();
	}
	
	@Bean
	GiphyApi giphy(){
		return new RestifyProxyBuilder().target(GiphyApi.class).build();
	}
}
