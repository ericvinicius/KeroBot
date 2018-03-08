package br.com.eric.telegram.kerobot.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChatModel {

	@Id
	private Long id;
	
	private String type;
	
	private String tittle;

	public ChatModel() {
	}

	public ChatModel(Long id, String title, String type) {
		this.id = id;
		tittle = title;
		this.type = type;
	}

	public Long getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTittle() {
		return tittle;
	}

	public void setName(String name) {
		this.tittle = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tittle == null) ? 0 : tittle.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatModel other = (ChatModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tittle == null) {
			if (other.tittle != null)
				return false;
		} else if (!tittle.equals(other.tittle))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	

	
}
