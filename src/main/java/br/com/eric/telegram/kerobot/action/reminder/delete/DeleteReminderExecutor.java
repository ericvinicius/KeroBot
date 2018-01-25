package br.com.eric.telegram.kerobot.action.reminder.delete;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.daos.ScheduledRepository;
import br.com.eric.telegram.kerobot.models.Scheduled;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;

@Component
public class DeleteReminderExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	@Autowired
	private ScheduledRepository scheduledRepository;

	public void deleteLast(Integer chatId, Integer userId) {
		Optional<Scheduled> scheduled = scheduledRepository.findFirstByChatIdAndUserIdOrderByIdDesc(chatId, userId);
		
		if (scheduled.isPresent()) {
			scheduledRepository.delete(scheduled.get());
			botApi.sendMessage(chatId, "Deletado! Aceito um chocolate!");
		} else {
			botApi.sendMessage(chatId, "Mas voce nem tinha um lembrete neste chat!!!");
		}
	}

}
