package com.instagram.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instagram.api.dto.post.PostDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.PostNotExistException;
import com.instagram.api.model.User;
import com.instagram.api.service.PostService;
import com.instagram.api.service.TokenService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	TokenService tokenService;
	
	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getPosts() {
		List<PostDto> body = postService.listPosts();
		return new ResponseEntity<List<PostDto>>(body, HttpStatus.OK);
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<List<PostDto>> getPostsByToken(@PathVariable("token") String token) {
        User user = tokenService.getUser(token);
        List<PostDto> posts = postService.listPostsByUser(user);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addPost(@RequestBody PostDto postDto,
    		@RequestParam("token") String token,
    		@RequestParam("image") MultipartFile image) throws AuthenticationFailException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
        postService.addPost(postDto, user, image);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post has been added"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{postID}")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody @Valid PostDto postDto,
            @RequestParam("token") String token,
            MultipartFile image) throws AuthenticationFailException,PostNotExistException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		postService.updatePost(postDto, user, image);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post has been updated"), HttpStatus.OK);
	}
    
	@DeleteMapping("/{postID}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postID") final Integer postID,
			@RequestParam("token") String token) throws AuthenticationFailException,PostNotExistException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		postService.deletePost(postID, user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post has been deleted"), HttpStatus.OK);
	}

}
