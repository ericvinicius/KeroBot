package br.com.eric.telegram.kerobot.daos;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.MessageModel;

public interface MessageRepository extends CrudRepository<MessageModel, Integer> {

}
