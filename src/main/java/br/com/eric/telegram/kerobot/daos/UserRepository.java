package br.com.eric.telegram.kerobot.daos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

	Optional<UserModel> findOneByUsername(String replaceAll);

}
