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

import com.instagram.api.dto.like.LikeDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.LikeNotExistException;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.service.LikeService;
import com.instagram.api.service.PostService;
import com.instagram.api.service.TokenService;

@RestController
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	TokenService tokenService;
	
	@GetMapping("/{postID}")
	public ResponseEntity<List<LikeDto>> getLikesByPost(@PathVariable("postID") Integer postId) {
		Post post = postService.getPost(postId).get();
		List<LikeDto> likes = likeService.listLikesByPost(post);
		return new ResponseEntity<List<LikeDto>>(likes, HttpStatus.OK);
	}
	
	@PostMapping("/add")
    public ResponseEntity<ApiResponse> addLike(@RequestBody LikeDto likeDto,
    		@RequestParam("token") String token) throws AuthenticationFailException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
        likeService.addLike(likeDto, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Like has been added"), HttpStatus.CREATED);
    }
	
	@DeleteMapping("/{likeID}")
	public ResponseEntity<ApiResponse> deleteLike(@PathVariable("likeID") final Integer likeID,
			@RequestParam("token") String token) throws AuthenticationFailException, LikeNotExistException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		likeService.deleteLike(likeID, user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Like has been deleted"), HttpStatus.OK);
	}

}
