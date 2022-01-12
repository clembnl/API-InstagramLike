package com.instagram.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.dto.follow.FollowDto;
import com.instagram.api.model.Follow;
import com.instagram.api.model.User;
import com.instagram.api.repository.FollowRepository;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepository;
	
	public List<FollowDto> getFollowersByUser(User user) {
		List<Follow> followersList = followRepository.getFollowersByUser(user);
		List<FollowDto> followers = new ArrayList<>();
		for (Follow follower:followersList) {
			FollowDto followDto = getDtoFromFollow(follower);
			followers.add(followDto);
		}
		return followers;
	}
	
	public List<FollowDto> getFollowingsByUser(User user) {
		List<Follow> followingsList = followRepository.getFollowingsByUser(user);
		List<FollowDto> followings = new ArrayList<>();
		for (Follow following:followingsList) {
			FollowDto followDto = getDtoFromFollow(following);
			followings.add(followDto);
		}
		return followings;
	}
	
	public void addFollowing(FollowDto followDto, User user) {
		Follow follow = getFollowFromDto(followDto);
		followRepository.save(follow);
	}
	
	public void deleteFollower(Integer id, User user) {
		followRepository.deleteById(id);
	}
	
	public void deleteFollowing(Integer id, User user) {
		followRepository.deleteById(id);
	}
	
	public static FollowDto getDtoFromFollow(Follow follow) {
		return new FollowDto(follow);
	}
	
	public static Follow getFollowFromDto(FollowDto followDto) {
		return new Follow(followDto);
	}

}
