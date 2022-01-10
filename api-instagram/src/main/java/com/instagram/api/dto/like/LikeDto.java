package com.instagram.api.dto.like;

import com.instagram.api.model.Like;

public class LikeDto {
	
	private Integer id;

	public LikeDto(Like like) {
	}
	
	public LikeDto() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
