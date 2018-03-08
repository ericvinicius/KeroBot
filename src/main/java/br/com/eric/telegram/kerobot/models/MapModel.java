package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MapModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private String chave;
	
	private String valor;
	
	@Deprecated
	public MapModel() {
	}
	
	public MapModel(Long userId, String key, String value) {
		this.userId = userId;
		this.chave = key;
		this.valor = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String key) {
		this.chave = key;
	}

	public String getValor() {
		return valor;
	}

	public MapModel setValor(String value) {
		this.valor = value;
		return this;
	}
	
	
}
