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
import br.com.eric.telegram.kerobot.models.InlineKeyboardButton;
import br.com.eric.telegram.kerobot.models.InlineKeyboardMarkup;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.MessageType;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class HoursExecutor {

	private static final ZoneId SP_ZONE_ID = ZoneId.of("America/Sao_Paulo");

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private HourRepository hourRepository;

	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LogManager.getLogger(HoursExecutor.class);
	private static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");

	public void enter(MessageModel message, String u) {
		String username = getUserId(message, u);
		enter(username);
		list(message);
	}

	private String getUserId(MessageModel message, String username) {
		// TODO: check if user is in chat (telegram method: getChatMember)
		return  username != null ? username : message.getFrom().getUsername();
	}

	public void exit(MessageModel message, String u) {
		String username = getUserId(message, u);
		exit(username);
		list(message);
	}

	public void list(MessageModel message) {
		StringBuilder builder = new StringBuilder();
		Integer chatId = message.getChat().getId();
		hourRepository.findByUserId(message.getFrom().getId()).forEach(h -> {
			
			String enter = h.getEnterHour() != null ? h.getEnterHour().format(FORMATTER_TIME) : "<SEM_REGISTRO>";
			String exit = h.getExitHour() != null ? h.getExitHour().format(FORMATTER_TIME) : "<SEM_REGISTRO>";
			
			builder.append(h.getDay()).append(" => ").append(enter).append(" | ").append(exit).append(" => ")
					.append(h.difference()).append("\n");
		});

		InlineKeyboardButton[] linha_1 = {new InlineKeyboardButton("Entrando :(", "/ponto_entrada"), new InlineKeyboardButton("Saindo :)", "/ponto_saida")};
		
		InlineKeyboardButton[][] buttons = {linha_1, {}}; 
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons);
		
		MessageType messageType = message.getType();
		String callback_query_id = messageType.getString("callback_query_id");
		
		Integer messageId = message.getMessageId();
		if (builder.length() == 0) {
			botApi.sendMessageOrEditMessage(chatId, "Voce nao possui horas registradas...", inlineKeyboardMarkup, messageType, messageId, callback_query_id);
		} else {
			botApi.sendMessageOrEditMessage(chatId, builder.toString(), inlineKeyboardMarkup, messageType, messageId, callback_query_id);
		}
	}

	public boolean enter(String username) {
		logger.info("entering... " + username);
		Optional<UserModel> u = userRepository.findOneByUsername(username.replaceAll("@", "").trim());
		if (u.isPresent()) {
			UserModel user = u.get();
			LocalDate today = LocalDate.now(SP_ZONE_ID);
			LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
			Optional<Hour> hour = hourRepository.findOneByDayAndUserId(today, user.getId());
			if (!hour.isPresent()) {
				hourRepository.save(new Hour(now, today, user.getId()));
				logger.info("entered! " + username);
				return true;
			}
		}
		return false;
	}
	
	public boolean exit(String username) {
		logger.info("exiting... " + username);
		Optional<UserModel> u = userRepository.findOneByUsername(username.replaceAll("@", "").trim());
		if (u.isPresent()) {
			UserModel user = u.get();
			LocalDate today = LocalDate.now(SP_ZONE_ID);
			LocalDateTime now = LocalDateTime.now(SP_ZONE_ID);
			Optional<Hour> hour = hourRepository.findOneByDayAndUserId(today, user.getId());
			if (hour.isPresent()) {
				Hour h = hour.get();
				h.setExit(now);
				hourRepository.save(h);
				logger.info("exited! " + username);
				return true;
			}
		}
		return false;
	}

}
