package br.com.eric.telegram.kerobot;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@SpringBootApplication(scanBasePackages = { "br.com.eric.telegram.kerobot" })
@EnableAutoConfiguration
@Configuration
public class Boot extends SpringBootServletInitializer {

	private static final Logger logger = LogManager.getLogger(Boot.class);

	public static void main(String[] args) {
		System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		SpringApplication.run(Boot.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Boot.class);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Bean
    TaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
