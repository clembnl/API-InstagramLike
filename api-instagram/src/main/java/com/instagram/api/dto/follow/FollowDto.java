package com.instagram.api.dto.follow;

import com.instagram.api.model.Follow;
import com.instagram.api.model.User;

public class FollowDto {
	
	private User follower;
	private User following;
	
	public FollowDto() {
	}
	
	public FollowDto(Follow follow) {
		this.setFollower(follow.getFollower());
		this.setFollowing(follow.getFollowing());
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowing() {
		return following;
	}

	public void setFollowing(User following) {
		this.following = following;
	}

}
