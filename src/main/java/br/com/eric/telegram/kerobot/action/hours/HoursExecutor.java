package br.com.eric.telegram.kerobot.action.hours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.HourRepository;
import br.com.eric.telegram.kerobot.daos.UserRepository;
import br.com.eric.telegram.kerobot.models.Hour;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class HoursExecutor {

	private static final ZoneId SP_ZONE_ID = ZoneId.of("America/Sao_Paulo");

	@Autowired
	private TelegramApi botApi;

	@Autowired
	private HourRepository hourRepository;

	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LogManager.getLogger(HoursExecutor.class);
	private static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");

	public void enter(Update update, String username) {
		Integer userId = getUserId(update, username);
		LocalDate today = LocalDate.now(SP_ZONE_ID);
		LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
		Optional<Hour> hour = hourRepository.findOneByDayAndUserId(today, userId);
		if (!hour.isPresent()) {
			hourRepository.save(new Hour(now, today, userId));
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de entrada registrado... " + now);
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de entrada ja esta registrado...");
		}
	}

	private Integer getUserId(Update update, String username) {
		logger.info("CHECKING USER...");
		if (username != null && !username.isEmpty()) {
			logger.info("...HAS NAME...");
			// TODO: check if user is in chat (telegram method: getChatMember)
			Optional<UserModel> user = userRepository.findOneByUsername(username.replaceAll("@", "").trim());
			if (user.isPresent()) {
				logger.info("...USER USED");
				return user.get().getId();
			} else {
				logger.info("...USER NOT USED");
				botApi.sendMessage(update.getMessage().getChat().getId(),
						"Usuario [" + username + "] nao encontrado, irei registrar no usuario @"
								+ update.getMessage().getFrom().getUsername());
			}
		}

		return update.getMessage().getFrom().getId();
	}

	public void exit(Update update, String username) {
		Integer userId = getUserId(update, username);
		LocalDate today = LocalDate.now(SP_ZONE_ID);
		LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
		Optional<Hour> hour = hourRepository.findOneByDayAndUserId(today, userId);
		if (hour.isPresent()) {
			Hour h = hour.get();
			h.setExit(now);
			hourRepository.save(h);
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de saida registrado... " + now);
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Voce não registrou entrada hoje...");
		}
	}

	public void list(Update update, String username) {
		Integer userId = getUserId(update, username);
		StringBuilder builder = new StringBuilder();
		hourRepository.findByUserId(userId).forEach(h -> {
			
			String enter = h.getEnterHour() != null ? h.getEnterHour().format(FORMATTER_TIME) : "<SEM_REGISTRO>";
			String exit = h.getEnterHour() != null ? h.getExitHour().format(FORMATTER_TIME) : "<SEM_REGISTRO>";
			
			builder.append(h.getDay()).append(" => ").append(enter).append(" | ").append(exit).append(" => ")
					.append(h.getHours()).append("\n");
		});

		if (builder.length() == 0) {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Voce nao possui horas registradas...");
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), builder.toString());
		}
	}

}
