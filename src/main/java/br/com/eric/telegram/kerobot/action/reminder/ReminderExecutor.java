package br.com.eric.telegram.kerobot.action.reminder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.daos.ScheduledRepository;
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

	public void execute(Scheduled s) {
		String msg = "@" + s.getUserName() + ", lembrete: " + s.getText();
		botApi.sendMessage(s.getChatId(), msg);
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
			scheduledRepository
					.save(new Scheduled(txt[0], dateTime, "Reminder", message.getFrom().getUsername(),
							message.getChat().getId(), message.getFrom().getId()));

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
}