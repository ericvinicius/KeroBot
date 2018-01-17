package br.com.eric.telegram.kerobot;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.controllers.GiphyApi;
import br.com.eric.telegram.kerobot.controllers.TelegramApi;
import br.com.eric.telegram.kerobot.filter.ShutdownFilter;

@Configuration
public class AppConfiguration {
	@Bean
	TaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	@Bean
	TelegramApi telegram() {
		return new RestifyProxyBuilder().target(TelegramApi.class).build();
	}

	@Bean
	GiphyApi giphy() {
		return new RestifyProxyBuilder().target(GiphyApi.class).build();
	}

	@Bean
	@ConditionalOnProperty(value = "endpoints.shutdown.enabled", havingValue = "true")
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new ShutdownFilter());
		registrationBean.setUrlPatterns(Collections.singleton("/shutdown"));
		return registrationBean;
	}
}
