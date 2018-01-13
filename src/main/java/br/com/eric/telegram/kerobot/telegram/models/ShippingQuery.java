package br.com.eric.telegram.kerobot.telegram.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingQuery {
	private String id;
	private User from;
	private String invoice_payload;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public String getInvoice_payload() {
		return invoice_payload;
	}

	public void setInvoice_payload(String invoice_payload) {
		this.invoice_payload = invoice_payload;
	}

	@Override
	public String toString() {
		return "ShippingQuery [id=" + id + ", from=" + from + ", invoice_payload=" + invoice_payload + "]";
	}

}
