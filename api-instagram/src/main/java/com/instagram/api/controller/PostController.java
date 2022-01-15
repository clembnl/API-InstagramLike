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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.dto.post.PostDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.PostNotExistException;
import com.instagram.api.model.User;
import com.instagram.api.service.PostService;
import com.instagram.api.service.TokenService;
import com.instagram.api.service.UserService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getPosts() {
		List<PostDto> body = postService.listPosts();
		return new ResponseEntity<List<PostDto>>(body, HttpStatus.OK);
	}
	
	@GetMapping("/{userID}")
	public ResponseEntity<List<PostDto>> getPostsByToken(@PathVariable("userID") Integer userID) {
        User user = userService.getUserById(userID).get();
        List<PostDto> posts = postService.listPostsByUser(user);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
    @PostMapping(value="/add") //, consumes = { "multipart/mixed", "multipart/form-data" }, produces = MediaType.MULTIPART_FORM_DATA_VALUE
    public ResponseEntity<ApiResponse> addPost(@RequestPart String postDtoString,
    		@RequestParam("token") String token,
    		@RequestPart("image") MultipartFile image) throws AuthenticationFailException, JsonMappingException, JsonProcessingException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		ObjectMapper objectMapper = new ObjectMapper();
		PostDto postDto = objectMapper.readValue(postDtoString, PostDto.class);
        postService.addPost(postDto, user, image);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post has been added"), HttpStatus.CREATED);
    }

    @PutMapping(value="/update/{postID}") //, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    public ResponseEntity<ApiResponse> updatePost(@RequestBody @Valid PostDto postDto,
    		@PathVariable("postID") Integer postId,
            @RequestParam("token") String token,
            @RequestPart("image") MultipartFile image) throws AuthenticationFailException,PostNotExistException {
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
