package br.com.eric.telegram.kerobot.scheduler;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.reminder.ReminderExecutor;
import br.com.eric.telegram.kerobot.daos.ScheduledRepository;

@Component
public class Scheduler {
	
	@Autowired
	private ReminderExecutor reminderExecutor;
	
	@Autowired
	private ScheduledRepository scheduledRepository;
	
	private static final Logger logger = LogManager.getLogger(Scheduler.class);
	
	@Scheduled(fixedRate = 10*1000)
	public void scheduleTaskWithFixedRate() {
		Iterable<br.com.eric.telegram.kerobot.models.Scheduled> scheduleds = scheduledRepository.findAllByTimeLessThan(new Date().getTime());
		scheduleds.forEach(s -> {
			reminderExecutor.execute(s);
			if (!s.isFrequently()) {
				scheduledRepository.delete(s);
				logger.info("Deletando lembrete que nao é recorrente...");
			}
		});
	}

}
