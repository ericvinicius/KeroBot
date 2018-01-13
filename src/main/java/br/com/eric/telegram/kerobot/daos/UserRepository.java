package br.com.eric.telegram.kerobot.daos;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

}
