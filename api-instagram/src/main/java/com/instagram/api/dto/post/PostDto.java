package com.instagram.api.dto.post;

import javax.validation.constraints.NotNull;

import com.instagram.api.model.Post;

public class PostDto {
	
	private Integer id;
	private @NotNull String imageUrl;
	private @NotNull String description;
	
	public PostDto(Post post) {
		this.setId(post.getId());
		this.setImageUrl(post.getImageUrl());
		this.setDescription(post.getDescription());
	}
	
	public PostDto(@NotNull String imageUrl, @NotNull String description) {
		this.imageUrl = imageUrl;
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
