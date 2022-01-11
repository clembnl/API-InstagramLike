package com.instagram.api.dto.post;

import javax.validation.constraints.NotNull;

public class PostDto {
	
	private Integer id;
	private String imageUrl;
	private @NotNull String description;
	
	public PostDto(@NotNull String description) {
		this.description = description;
	}
	
	public PostDto() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
