package br.com.eric.telegram.kerobot.daos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.Scheduled;

public interface ScheduledRepository extends CrudRepository<Scheduled, Integer> {

	Iterable<Scheduled> findAllByTimeLessThan(long time);

	void deleteByChatIdAndUserId(Integer chatId, Integer userId);

	Optional<Scheduled> findFirstByChatIdAndUserIdOrderByIdDesc(Integer chatId, Integer userId);

}
