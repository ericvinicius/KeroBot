package br.com.eric.telegram.kerobot.daos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Long> {

	Optional<UserModel> findOneByUsername(String replaceAll);

}
