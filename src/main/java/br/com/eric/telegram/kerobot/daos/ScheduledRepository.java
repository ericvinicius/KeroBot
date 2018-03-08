package br.com.eric.telegram.kerobot.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.Scheduled;

public interface ScheduledRepository extends CrudRepository<Scheduled, Long> {

	Iterable<Scheduled> findAllByTimeLessThan(long time);

	void deleteByChatIdAndUserId(Long chatId, Long userId);

	Optional<Scheduled> findFirstByChatIdAndUserIdOrderByIdDesc(Long chatId, Long userId);

	List<Scheduled> findAllByChatIdAndUserIdOrderByIdDesc(Long id, Long id2);

}
