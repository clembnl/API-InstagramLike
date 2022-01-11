package com.instagram.api.dto.post;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

//import com.instagram.api.model.Post;

public class PostDto {
	
	private Integer id;
	private @NotNull MultipartFile image;
	private String imageUrl;
	private @NotNull String description;
	
	/*
	public PostDto(Post post) {
		this.setId(post.getId());
		this.setImage();
		//this.setImageUrl(post.getImageUrl());
		this.setDescription(post.getDescription());
	}
	*/
	
	public PostDto(@NotNull MultipartFile image, @NotNull String description) { //@NotNull String imageUrl
		//this.imageUrl = imageUrl;
		this.image = image;
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
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
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
