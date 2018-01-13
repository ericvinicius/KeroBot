package br.com.eric.telegram.kerobot.daos;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.ChatModel;

public interface ChatRepository extends CrudRepository<ChatModel, Integer> {

}
