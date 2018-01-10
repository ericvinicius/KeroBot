package br.com.eric.telegram.kerobot;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
@Controller
public class Boot {

	private static final Logger logger = LogManager.getLogger(Boot.class);

	public static void main(String[] args) {
		System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		SpringApplication.run(Boot.class, args);
	}

	@GetMapping("/")
	@ResponseBody
	public String home() {
		return "home";
	}
}
