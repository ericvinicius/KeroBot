package br.com.eric.telegram.kerobot.action.reminder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.daos.ScheduledRepository;
import br.com.eric.telegram.kerobot.models.InlineKeyboardButton;
import br.com.eric.telegram.kerobot.models.InlineKeyboardMarkup;
import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.models.Scheduled;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;
import br.com.eric.telegram.kerobot.util.StringUtil;
import br.com.eric.telegram.kerobot.util.Unit;

@Service
public class ReminderExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private ScheduledRepository scheduledRepository;

	private static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final ZoneId SP_ZONE_ID = ZoneId.of("America/Sao_Paulo");
	private static final Logger logger = LogManager.getLogger(ReminderExecutor.class);

	public void execute(Scheduled s) {
		String msg = "@" + s.getUserName() + ", lembrete: " + s.getText();
		botApi.sendMessage(s.getChatId(), msg, createButtons(s.getPeriod()));
	}

	private InlineKeyboardMarkup createButtons(Long lastTime) {
		InlineKeyboardButton[] linha_1 = { new InlineKeyboardButton("+15m", "/snooze_reminder_15m"),
				new InlineKeyboardButton("+1h", "/snooze_reminder_1h"),
				new InlineKeyboardButton("+3h", "/snooze_reminder_3h"),
				new InlineKeyboardButton("+1d", "/snooze_reminder_1d"),
				new InlineKeyboardButton("end", "/snooze_reminder_cancel") };

		Unit unit = Unit.getFor(lastTime);
		if (unit != null) {
			int times = (int) (lastTime / unit.getTime());
			linha_1[3] = new InlineKeyboardButton("+=", "/snooze_reminder_" + times + unit.getNames().get(0));
		}

		InlineKeyboardButton[][] buttons = { linha_1, {} };
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons);
		return inlineKeyboardMarkup;
	}

	public void saveReminder(MessageModel message, Matcher matcher) {
		try {
			String[] txt = getParts(message.getText(), matcher);
			if (txt[0] == null || txt[0].isEmpty()) {
				botApi.sendMessage(message.getChat().getId(), "É para lembrar do que? Aceito doces...");
				return;
			}

			Integer time = StringUtil.getIntergers(txt[1]);
			Unit unit = Unit.getFor(StringUtil.removeNumbers(txt[1]));

			Date dateTime = unit.getNextDateFor(time);
			scheduledRepository.save(new Scheduled(txt[0], dateTime, "Reminder", message.getFrom().getUsername(),
					message.getChat().getId(), message.getFrom().getId(), unit.getTime() * time));

			LocalDateTime date = Instant.ofEpochMilli(dateTime.getTime()).atZone(SP_ZONE_ID).toLocalDateTime();
			botApi.sendMessage(message.getChat().getId(), "Lembrete registrado em " + date.format(FORMATTER_TIME));
		} catch (Exception e) {
			botApi.sendMessage(message.getChat().getId(), e.getMessage());
		}
	}

	private String[] getParts(String txt, Matcher matcher) {
		String[] parts = new String[2];
		parts[0] = matcher.group("start");
		parts[1] = matcher.group("end");
		return parts;
	}

	public void listReminders(MessageModel message) {
		StringBuilder builder = new StringBuilder();
		Integer chatId = message.getChat().getId();
		Integer userId = message.getFrom().getId();

		builder.append("Seus Lembretes: \n");
		List<Scheduled> reminders = scheduledRepository.findAllByChatIdAndUserIdOrderByIdDesc(chatId, userId);
		for (Scheduled scheduled : reminders) {
			LocalDateTime date = Instant.ofEpochMilli(scheduled.getTime()).atZone(SP_ZONE_ID).toLocalDateTime();
			builder.append(date.format(FORMATTER_TIME)).append(" | ").append(scheduled.getText()).append("\n");
		}

		String txt = builder.toString();
		if (reminders.isEmpty()) {
			txt = "Voce nao possui lembretes, neste chat";
		}

		botApi.sendMessage(chatId, txt);
	}

	public void snooze(MessageModel message, Matcher matcher) {
		String snooze = matcher.group("end");
		String originalMessage = message.getType().getString("message_text");
		String messageToSave = originalMessage.replaceAll(".*lembrete: ", "");
		Date dateTime;

		try {
			Integer time = StringUtil.getIntergers(snooze);
			Unit unit = Unit.getFor(StringUtil.removeNumbers(snooze));

			dateTime = unit.getNextDateFor(time);
			scheduledRepository.save(new Scheduled(messageToSave, dateTime, "Reminder", message.getFrom().getUsername(),
					message.getChat().getId(), message.getFrom().getId(), unit.getTime() * time));
		} catch (NumberFormatException nfe) {
			logger.info("Terminando acoes de snooze");
			botApi.editMessage(message.getChat().getId(), message.getMessageId(), originalMessage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			botApi.editMessage(message.getChat().getId(), message.getMessageId(), "Falha ao adiar!");
			return;
		}

		String when = Instant.ofEpochMilli(dateTime.getTime()).atZone(SP_ZONE_ID).toLocalDateTime()
				.format(FORMATTER_TIME);
		botApi.editMessage(message.getChat().getId(), message.getMessageId(), "Lembrete adiado para " + when);
	}
}