package com.instagram.api.dto.like;

import javax.validation.constraints.NotNull;

import com.instagram.api.model.Like;

public class LikeDto {
	
	private Integer id;
	private @NotNull Integer postId;
	
	public LikeDto() {
	}

	public LikeDto(Like like) {
		this.setPostId(like.getPost().getId());
	}
	
	public LikeDto(@NotNull Integer postId) {
		this.postId = postId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

}
