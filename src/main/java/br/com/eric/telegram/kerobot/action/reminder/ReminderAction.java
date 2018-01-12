package br.com.eric.telegram.kerobot.action.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.action.TelegramBot;
import br.com.eric.telegram.kerobot.models.Chat;
import br.com.eric.telegram.kerobot.models.Message;
import br.com.eric.telegram.kerobot.models.Update;
import br.com.eric.telegram.kerobot.models.User;

public class ReminderAction extends Action {

	private TelegramBot botApi = new RestifyProxyBuilder().target(TelegramBot.class).build();

	@Autowired
	TaskScheduler taskScheduler;

	ScheduledFuture<?> scheduledFuture;

	@Override
	public void execute(Update update) {
		super.info("ReminderAction");
		try {
			String txt = update.getText().get();
			String txtTime = getTimePart(txt);
			Integer time = Integer.parseInt(txtTime.replaceAll("\\D+", ""));
			String unit = txtTime.trim().replaceAll("\\d+", "");

			CronTrigger cronTrigger = new CronTrigger("0/" + time + " * * * * *");

			scheduledFuture = taskScheduler.schedule(doIt(update), cronTrigger);

			String msg = "espera de " + time;
			botApi.send(TOKEN, update.getMessage().getChat().getId(), msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable doIt(Update update) {
		class OneShotTask implements Runnable {
			Update update;

			OneShotTask(Update update) {
				this.update = update;
			}

			public void run() {
				String msg = "ola " + update.getMessage().getFrom().getFirst_name() + ", para lembrar as: "
						+ update.getMessage().getText();
				botApi.send(TOKEN, update.getMessage().getChat().getId(), msg);
			}
		}
		return new Thread(new OneShotTask(update));
	}

	private String getTimePart(String txt) {
		for (String token : getPatterns()) {
			if (txt.matches(token)) {
				String goodToken = getGoodToken(token);
				int indexOf = txt.indexOf(goodToken);
				txt = txt.substring(indexOf, txt.length()).replaceAll(goodToken, "");
				break;
			}
		}
		return txt;
	}

	private String getGoodToken(String token) {
		return token.replaceAll("\\.|\\*|\\(|\\||\\)", "");
	}

	@Override
	public List<String> getPatterns() {
		return Arrays.asList(".*lembrar em.*", ".*avise em.*", ".*avise daqui.*", ".*lembre em.*");
	}

	public static void main(String[] args) {
		User user = new User();
		user.setFirst_name("Bot Teste");

		Chat chat = new Chat();
		chat.setId(-291752404);

		Message message = new Message();
		message.setText("oi lembrar em 15m");
		message.setChat(chat);
		message.setFrom(user);

		Update update = new Update();
		update.setMessage(message);

		ReminderAction reminderAction = new ReminderAction();
		reminderAction.execute(update);
	}

}
