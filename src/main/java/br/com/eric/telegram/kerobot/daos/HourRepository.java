package br.com.eric.telegram.kerobot.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.Hour;

public interface HourRepository extends CrudRepository<Hour, Integer> {

	List<Hour> findByUserId(Integer id);

	Optional<Hour> findOneByDay(LocalDate today);

}
