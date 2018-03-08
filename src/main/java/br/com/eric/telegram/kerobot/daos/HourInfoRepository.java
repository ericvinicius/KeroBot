package br.com.eric.telegram.kerobot.daos;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.HourInfo;

public interface HourInfoRepository extends CrudRepository<HourInfo, Long> {

	HourInfo findOneByUserId(Long userId);

}
