package br.com.eric.telegram.kerobot.action.hours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.HourRepository;
import br.com.eric.telegram.kerobot.models.Hour;
import br.com.eric.telegram.kerobot.telegram.TelegramApi;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class HoursExecutor {

	private static final ZoneId SP_ZONE_ID = ZoneId.of("America/Sao_Paulo");

	@Autowired
	private TelegramApi botApi;

	@Autowired
	private HourRepository hourRepository;

	private static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");

	public void enter(Update update) {
		LocalDate today = LocalDate.now(SP_ZONE_ID);
		LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
		Optional<Hour> hour = hourRepository.findOneByDay(today);
		if (!hour.isPresent()) {
			hourRepository.save(new Hour(now, today, update.getMessage().getFrom().getId()));
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de entrada registrado... " + now);
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de entrada ja esta registrado...");
		}
	}

	public void exit(Update update) {
		LocalDate today = LocalDate.now(SP_ZONE_ID);
		LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
		Optional<Hour> hour = hourRepository.findOneByDay(today);
		if (hour.isPresent()) {
			Hour h = hour.get();
			h.setExit(now);
			hourRepository.save(h);
			botApi.sendMessage(update.getMessage().getChat().getId(), "Horario de saida registrado... " + now);
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Voce não registrou entrada hoje...");
		}
	}

	public void list(Update update) {
		StringBuilder builder = new StringBuilder();
		hourRepository.findByUserId(update.getMessage().getFrom().getId()).forEach(h -> {
			builder.append(h.getDay()).append(" => ").append(h.getEnterHour().format(FORMATTER_TIME)).append(" | ")
					.append(h.getExitHour().format(FORMATTER_TIME)).append(" => ").append(h.getHours()).append("\n");
		});

		if (builder.length() == 0) {
			botApi.sendMessage(update.getMessage().getChat().getId(), "Voce nao possui horas registradas...");
		} else {
			botApi.sendMessage(update.getMessage().getChat().getId(), builder.toString());
		}
	}

}
