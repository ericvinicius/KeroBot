package br.com.eric.telegram.kerobot.daos;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.Scheduled;

public interface ScheduledRepository extends CrudRepository<Scheduled, Integer> {

	Iterable<Scheduled> findAllByTimeLessThan(long time);

}
