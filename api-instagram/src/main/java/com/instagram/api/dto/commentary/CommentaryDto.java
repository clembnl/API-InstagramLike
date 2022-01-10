package com.instagram.api.dto.commentary;

import javax.validation.constraints.NotNull;

import com.instagram.api.model.Commentary;

public class CommentaryDto {
	
	private Integer id;
	private @NotNull String commentary;
	private @NotNull Integer postId;

	public CommentaryDto(@NotNull String commentary, @NotNull Integer postId) {
		this.commentary = commentary;
		this.postId = postId;
	}
	
	public CommentaryDto(Commentary com) {
		this.setId(com.getId());
		this.setCommentary(com.getCommentary());
		this.setPostId(com.getPost().getId());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}
}
