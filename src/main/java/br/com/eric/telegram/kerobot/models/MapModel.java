package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MapModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer userId;
	
	private String chave;
	
	private String valor;
	
	@Deprecated
	public MapModel() {
	}
	
	public MapModel(Integer userId, String key, String value) {
		this.userId = userId;
		this.chave = key;
		this.valor = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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
