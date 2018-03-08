package br.com.eric.telegram.kerobot.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.MapModel;

public interface MapRepository extends CrudRepository<MapModel, Long> {

	Optional<MapModel> findByUserIdAndChave(Long userId, String chave);

	List<MapModel> findAllByUserId(Long id);

}
