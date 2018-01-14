package br.com.eric.telegram.kerobot.gifhy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String type;
	private String id;
	private String url;
	private String image_original_url;
	private String image_url;
	private String image_mp4_url;
	private String image_frames;
	private String image_width;
	private String image_height;
	private String username;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage_original_url() {
		return image_original_url;
	}

	public void setImage_original_url(String image_original_url) {
		this.image_original_url = image_original_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_mp4_url() {
		return image_mp4_url;
	}

	public void setImage_mp4_url(String image_mp4_url) {
		this.image_mp4_url = image_mp4_url;
	}

	public String getImage_frames() {
		return image_frames;
	}

	public void setImage_frames(String image_frames) {
		this.image_frames = image_frames;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_height() {
		return image_height;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
