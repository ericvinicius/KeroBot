package br.com.eric.telegram.kerobot.action.hours;

import static br.com.eric.telegram.kerobot.util.DateUtil.SP_ZONE_ID;
import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.HourInfoRepository;
import br.com.eric.telegram.kerobot.daos.HourRepository;
import br.com.eric.telegram.kerobot.daos.UserRepository;
import br.com.eric.telegram.kerobot.models.Hour;
import br.com.eric.telegram.kerobot.models.HourInfo;
import br.com.eric.telegram.kerobot.models.InlineKeyboardButton;
import br.com.eric.telegram.kerobot.models.InlineKeyboardMarkup;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.MessageType;
import br.com.eric.telegram.kerobot.models.UserModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class HoursExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private HourRepository hourRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HourInfoRepository hourInfoRepository;
	
	private static final Logger logger = LogManager.getLogger(HoursExecutor.class);
	private static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");

	public void enter(MessageModel message) {
		boolean enter = enter(message.getFrom().getUsername());
		if (enter) {
			list(message);
		} else {
			botApi.sendMessage(message.getChat().getId(), "Voce ja entrou hoje!");
		}
	}

	public void exit(MessageModel message) {
		boolean exit = exit(message.getFrom().getUsername());
		if (exit) {
			list(message);
		} else {
			botApi.sendMessage(message.getChat().getId(), "Voce ja entrou no trabalho para sair...?");
		}

	}

	public void list(MessageModel message) {
		Long chatId = message.getChat().getId();
		message.getFrom().getId();
		List<Hour> hours = hourRepository.findByUserId(message.getFrom().getId());
		HourInfo hourInfo = hourInfoRepository.findOneByUserId(message.getFrom().getId());
		
		Long extraMins = 0L;
		StringBuilder builder = new StringBuilder();
		builder.append("Horas de @").append(message.getFrom().getUsername()).append("\n");
		for (Hour h : hours) {
			String enter = h.getEnterHour() != null ? h.getEnterHour().format(FORMATTER_TIME) : "__:__";
			String exit = h.getExitHour() != null ? h.getExitHour().format(FORMATTER_TIME) : "__:__";
			Long minutesInHour = hourInfo.getExtraFor(h);
			
			builder.append(h.getDay()).append(" => ").append(enter).append(" | ").append(exit).append(" => ")
					.append(h.difference()).append(" | ").append(String.format("%d:%02d", minutesInHour/60, minutesInHour%60)).append("\n");
			extraMins += minutesInHour;
		}
		Long extraHours = extraMins / 60L;
		Long extraMinutes = extraMins % 60L;
		builder.append("Extras: " + String.format("%d:%02d", extraHours, extraMinutes) + "\n");
		InlineKeyboardMarkup inlineKeyboardMarkup = createButtons();

		MessageType messageType = message.getType();
		String callback_query_id = messageType.getString("callback_query_id");

		Long messageId = message.getMessageId();
		if (hours.isEmpty()) {
			botApi.sendMessageOrEditMessage(chatId, "Voce nao possui horas registradas...", inlineKeyboardMarkup,
					messageType, messageId, callback_query_id);
		} else {
			botApi.sendMessageOrEditMessage(chatId, builder.toString(), inlineKeyboardMarkup, messageType, messageId,
					callback_query_id);
		}
	}

	private InlineKeyboardMarkup createButtons() {
		InlineKeyboardButton[] linha_1 = { 
				new InlineKeyboardButton(parseToUnicode(":arrow_up:"), "/ponto_entrada"),
				new InlineKeyboardButton(parseToUnicode(":arrow_down:"), "/ponto_saida"),
				new InlineKeyboardButton(parseToUnicode(":recycle:"), "/ponto_refresh"), 
			};

		InlineKeyboardButton[][] buttons = { linha_1, {} };
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons);
		return inlineKeyboardMarkup;
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

	public void refresh(MessageModel message) {
		list(message);
	}

	public void edit(MessageModel message, Matcher matcher) {
		String hourTxt = matcher.group("hour");
		String action = matcher.group("action");
		logger.info("hora = [" + hourTxt + "]");
		logger.info("action = [" + action + "]");

		Optional<UserModel> u = userRepository
				.findOneByUsername(message.getFrom().getUsername().replaceAll("@", "").trim());
		if (u.isPresent()) {
			UserModel user = u.get();
			LocalDate today = LocalDate.now(SP_ZONE_ID);
			Optional<Hour> hour = hourRepository.findOneByDayAndUserId(today, user.getId());
			hour.ifPresent(h -> {
				logger.info("diference: " + h.difference());
				int add = 0;
				if (action.equals("-")) {
					logger.info("Subtract");
					add = -10;
				} else if (action.equals("+")) {
					logger.info("Add");
					add = 10;
				}

				if (hourTxt.equals("entrada")) {
					logger.info("Enter");
					h.enterAddMinutes(add);
				} else if (hourTxt.equals("saida")) {
					logger.info("Exit");
					h.exitaddMinutes(add);
				}

				logger.info("diference after: " + h.difference());
				hourRepository.save(h);
				list(message);
			});
		}

	}

	public void editEspecific(MessageModel message, Matcher matcher) {
		String action = matcher.group("action");
		String hourTxt = matcher.group("hour");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(action, formatter);
		
		Optional<UserModel> u = userRepository
				.findOneByUsername(message.getFrom().getUsername().replaceAll("@", "").trim());
		if (u.isPresent()) {
			UserModel user = u.get();
			Optional<Hour> hour = hourRepository.findOneByDayAndUserId(dateTime.toLocalDate(), user.getId());
			Hour h;
			if (hour.isPresent()) {
				h = hour.get();
			} else {
				h = new Hour(dateTime.toLocalDate(), user.getId());
			}
			
			if (hourTxt.equals("entrada")) {
				logger.info("Enter");
				h.setEnter(dateTime);
			} else if (hourTxt.equals("saida")) {
				logger.info("Exit");
				h.setExit(dateTime);
			}
			hourRepository.save(h);
			list(message);
		}
		
	}

}
