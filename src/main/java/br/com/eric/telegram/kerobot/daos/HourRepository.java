package br.com.eric.telegram.kerobot.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.Hour;

public interface HourRepository extends CrudRepository<Hour, Long> {

	List<Hour> findByUserId(Long id);

	Optional<Hour> findOneByDayAndUserId(LocalDate today, Long userId);

}
