package com.instagram.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.api.dto.follow.FollowDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.model.User;
import com.instagram.api.service.FollowService;
import com.instagram.api.service.TokenService;
import com.instagram.api.service.UserService;

@RestController
@RequestMapping("/follow")
public class FollowController {
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/followers/{userID}")
	public ResponseEntity<List<FollowDto>> getFollowersByUser(@PathVariable("userID") Integer userId) {
		User user = userService.getUserById(userId).get();
		List<FollowDto> followers = followService.getFollowersByUser(user);
		return new ResponseEntity<List<FollowDto>>(followers, HttpStatus.OK);
	}
	
	@GetMapping("/followings/{userID}")
	public ResponseEntity<List<FollowDto>> getFollowingsByUser(@PathVariable("userID") Integer userId) {
		User user = userService.getUserById(userId).get();
		List<FollowDto> followings = followService.getFollowingsByUser(user);
		return new ResponseEntity<List<FollowDto>>(followings, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addFollowing(@RequestBody FollowDto followDto,
    		@RequestParam("token") String token) throws AuthenticationFailException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		followService.addFollowing(followDto, user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Following has been added"), HttpStatus.CREATED);
	}
	
	@DeleteMapping("following/delete/{followID}")
	public ResponseEntity<ApiResponse> deleteFollowing(@PathVariable("followID") final Integer followID,
			@RequestParam("token") String token) throws AuthenticationFailException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		followService.deleteFollowing(followID, user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Following has been deleted"), HttpStatus.OK);
	}
	
	@DeleteMapping("follower/delete/{followID}")
	public ResponseEntity<ApiResponse> deleteFollower(@PathVariable("followID") final Integer followID,
			@RequestParam("token") String token) throws AuthenticationFailException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		followService.deleteFollower(followID, user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Follower has been deleted"), HttpStatus.OK);
	}

}
