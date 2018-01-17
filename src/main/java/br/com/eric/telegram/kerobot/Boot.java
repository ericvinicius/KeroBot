package br.com.eric.telegram.kerobot;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Boot extends SpringBootServletInitializer {

	private static final Logger logger = LogManager.getLogger(Boot.class);

	public static void main(String[] args) {
//		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "heroku");
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

}
