package br.com.eric.telegram.kerobot.action;

import org.springframework.data.repository.CrudRepository;

import br.com.eric.telegram.kerobot.models.ChatModel;

public interface ChatRepository extends CrudRepository<ChatModel, Integer> {

}
