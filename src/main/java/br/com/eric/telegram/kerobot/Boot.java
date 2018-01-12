package br.com.eric.telegram.kerobot;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Boot {

	private static final Logger logger = LogManager.getLogger(Boot.class);

	public static void main(String[] args) {
		System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		SpringApplication.run(Boot.class, args);
	}

	@Bean
    TaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
