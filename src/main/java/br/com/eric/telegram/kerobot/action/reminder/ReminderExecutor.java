package br.com.eric.telegram.kerobot.action.reminder;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		botApi.sendMessage(s.getChatId(), msg, createButtons(s));
		if (s.isFrequently()) {
			s.refresh();
			scheduledRepository.save(s);
		}
	}

	private InlineKeyboardMarkup createButtons(Scheduled s) {
		Long period = s.getPeriod();
		InlineKeyboardButton[][] buttons;
		if (!s.isFrequently()) {
			InlineKeyboardButton[] linha_1 = new InlineKeyboardButton[] {
					new InlineKeyboardButton("+15m", "/snooze_reminder_15m"),
					new InlineKeyboardButton("+1h", "/snooze_reminder_1h"),
					new InlineKeyboardButton("+3h", "/snooze_reminder_3h"),
					new InlineKeyboardButton("+1d", "/snooze_reminder_1d"),
					new InlineKeyboardButton(parseToUnicode(":x:"), "/snooze_reminder_cancel") };

			Unit unit = Unit.getFor(period);
			if (unit != null) {
				int times = (int) (period / unit.getTime());
				linha_1[3] = new InlineKeyboardButton(parseToUnicode(":recycle:"),
						"/snooze_reminder_" + times + unit.getNames().get(0));
			}
			buttons = new InlineKeyboardButton[][] { linha_1, {} };
		} else {
			InlineKeyboardButton[] linha_1 = new InlineKeyboardButton[] {
					new InlineKeyboardButton("+15m", "/snooze_reminder_15m"),
					new InlineKeyboardButton("+1h", "/snooze_reminder_1h") };
			InlineKeyboardButton[] linha_2 = new InlineKeyboardButton[] {
					new InlineKeyboardButton("Cancelar Recorrente", "/frequently_reminder_cancel_" + s.getId()),
					new InlineKeyboardButton(parseToUnicode(":x:"), "/snooze_reminder_cancel") };
			buttons = new InlineKeyboardButton[][] { linha_1, linha_2 };
		}

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

			if (txt[1] == null || txt[1].isEmpty()) {
				botApi.sendMessage(message.getChat().getId(), "É para lembrar quando? Aceito doces...");
				return;
			}

			Date nowDate = new Date();
			long now = nowDate.getTime();
			Date dateTime = getNextDateForText(txt[1], nowDate);

			Scheduled remind = new Scheduled(txt[0], dateTime, "Reminder", message.getFrom().getUsername(),
					message.getChat().getId(), message.getFrom().getId(), dateTime.getTime() - now);
			remind = scheduledRepository.save(remind);

			LocalDateTime date = Instant.ofEpochMilli(dateTime.getTime()).atZone(SP_ZONE_ID).toLocalDateTime();
			botApi.sendMessage(message.getChat().getId(), "Lembrete registrado em " + date.format(FORMATTER_TIME),
					createButtonsOfReminderRegistered(remind));
		} catch (Exception e) {
			botApi.sendMessage(message.getChat().getId(), "Não consegui registrar o lembrete... " + e.getMessage());
		}
	}

	private InlineKeyboardMarkup createButtonsOfReminderRegistered(Scheduled remind) {
		InlineKeyboardButton[] linha_1 = {
				new InlineKeyboardButton("Recorrente", "/frequently_reminder_" + remind.getId()),
				new InlineKeyboardButton(parseToUnicode(":no_entry:"), "/reminder_delete_" + remind.getId()),
				new InlineKeyboardButton(parseToUnicode(":x:"), "/snooze_reminder_cancel") };

		InlineKeyboardButton[][] buttons = { linha_1, {} };
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons);
		return inlineKeyboardMarkup;
	}

	private static Date getNextDateForText(String text, Date date) {
		Matcher matcher = Pattern.compile("\\d+").matcher(text);
		String[] split = Arrays.stream(text.replaceAll(" e ", "").split("\\d")).filter(x -> !x.trim().isEmpty())
				.toArray(String[]::new);
		Date from = date;
		for (int i = 0; i < split.length; i++) {
			String unitTxt = split[i].trim();

			if (matcher.find()) {
				Integer time = Integer.parseInt(matcher.group());
				Unit unit = Unit.getFor(unitTxt);
				from = unit.getNextDateForFrom(time, from);
			} else {
				throw new IllegalArgumentException("Parece que sua mensagem esta meio estranha...");
			}
		}
		return from;
	}

	// public static void main(String[] args) {
	// Date date = new Date();
	// Date nextDateForText = getNextDateForText("5 dias e 3horas e 2 minutos 1
	// s", date);
	// System.out.println(computeDiff(date, nextDateForText));
	// }
	//
	// public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
	// long diffInMillies = date2.getTime() - date1.getTime();
	// List<TimeUnit> units = new
	// ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
	// Collections.reverse(units);
	// Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	// long milliesRest = diffInMillies;
	// for ( TimeUnit unit : units ) {
	// long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	// long diffInMilliesForUnit = unit.toMillis(diff);
	// milliesRest = milliesRest - diffInMilliesForUnit;
	// result.put(unit,diff);
	// }
	// return result;
	// }

	private String[] getParts(String txt, Matcher matcher) {
		String[] parts = new String[2];
		parts[0] = matcher.group("start");
		parts[1] = matcher.group("end");
		return parts;
	}

	public void listReminders(MessageModel message) {
		StringBuilder builder = new StringBuilder();
		Long chatId = message.getChat().getId();
		Long userId = message.getFrom().getId();

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
		botApi.editMessage(message.getChat().getId(), message.getMessageId(), originalMessage + "\n\nLembrete adiado para " + when);
	}

	public void frequently(MessageModel message, Matcher matcher) {
		String group = matcher.group("end").trim();
		Long scheduledId = Long.parseLong(group);
		Scheduled scheduled = scheduledRepository.findOne(scheduledId);
		if (scheduled != null) {
			scheduled.setFrequently(true);
			scheduledRepository.save(scheduled);
			logger.info("Salvando lembrete como recorrente...");
			botApi.editMessage(message.getChat().getId(), message.getMessageId(), "Lembrete agora é recorrente!");
		}
	}

	public void frequentlyCancel(MessageModel message, Matcher matcher) {
		Scheduled scheduled = scheduledRepository.findOne(Long.parseLong(matcher.group("end").trim()));
		if (scheduled != null) {
			scheduled.setFrequently(false);
			scheduledRepository.save(scheduled);
			logger.info("Removendo lembrete como recorrente...");
			botApi.editMessage(message.getChat().getId(), message.getMessageId(), "Lembrete NÃO é mais recorrente!");
		}
	}

	public void cancelReminder(MessageModel message, Matcher matcher) {
		Scheduled scheduled = scheduledRepository.findOne(Long.parseLong(matcher.group("end").trim()));
		if (scheduled != null) {
			scheduledRepository.delete(scheduled);
			logger.info("deletando lembrete...");
			botApi.editMessage(message.getChat().getId(), message.getMessageId(), "Lembrete cancelado!");
		}
	}
}