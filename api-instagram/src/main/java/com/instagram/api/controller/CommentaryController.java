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

import com.instagram.api.dto.commentary.CommentaryDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.CommentaryNotExistException;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.service.CommentaryService;
import com.instagram.api.service.PostService;
import com.instagram.api.service.TokenService;

@RestController
@RequestMapping("/commentary")
public class CommentaryController {
	
	@Autowired
	private CommentaryService commentaryService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/{postID}")
	public ResponseEntity<List<CommentaryDto>> getComsByPost(@PathVariable("postID") Integer postId) {
		Post post = postService.getPost(postId).get();
		List<CommentaryDto> commentarys = commentaryService.listCommentarysByPost(post);
		return new ResponseEntity<List<CommentaryDto>>(commentarys, HttpStatus.OK);
	}
	
	@PostMapping("/add")
    public ResponseEntity<ApiResponse> addCom(@RequestBody CommentaryDto commentaryDto) {
        commentaryService.addCommentary(commentaryDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Commentary has been added"), HttpStatus.CREATED);
    }
	
    @PutMapping("/update/{comID}")
    public ResponseEntity<ApiResponse> updateCommentary(@RequestBody @Valid CommentaryDto comDto,
            @RequestParam("token") String token) throws AuthenticationFailException,CommentaryNotExistException {
		tokenService.authenticate(token);
		User user = tokenService.getUser(token);
		Post post = postService.getPostById(comDto.getPostId());
		commentaryService.updateCommentary(comDto, user, post);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Commentary has been updated"), HttpStatus.OK);
	}
	
	@DeleteMapping("/{comID}")
	public void deleteCommentary(@PathVariable("commentaryID") final Integer commentaryID) {
		commentaryService.deleteCommentary(commentaryID);
	}

}
