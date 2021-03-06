package com.instagram.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.instagram.api.dto.post.PostDto;
import com.instagram.api.exception.PostNotExistException;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired 
	private StorageService storageService;
	
	public List<PostDto> listPosts() {
        Iterable<Post> products = postRepository.findAll();
        List<PostDto> productDtos = new ArrayList<>();
        for(Post product : products) {
            PostDto productDto = getDtoFromPost(product);
            productDtos.add(productDto);
        }
        return productDtos;
	}
	
    public List<PostDto> listPostsByUser(User user) {
        List<Post> postList = postRepository.findAllByUser(user);
        List<PostDto> posts = new ArrayList<>();
        for (Post post:postList){
            PostDto postDto = getDtoFromPost(post);
            posts.add(postDto);
        }
        return posts;
    }
    
    public Post getPostById(Integer postId) throws PostNotExistException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent())
            throw new PostNotExistException("Post id is invalid " + postId);
        return optionalPost.get();
    }
    
    public Optional<Post> getPost(final Integer postId) {
    	return postRepository.findById(postId);
    }
    
    public void addPost(PostDto postDto, User user, MultipartFile image) {
        Post post = getPostFromDto(postDto, user, image);
        postRepository.save(post);
    }
    
    public void updatePost(PostDto postDto, User user, MultipartFile image) {
        Post post = postRepository.findById(postDto.getId()).get();
        post.setImageName(System.currentTimeMillis() + "_" + image.getOriginalFilename());
        post.setImageUrl(storageService.uploadFile(image, post.getImageName()));
        post.setDescription(postDto.getDescription());
        postRepository.save(post);
    }
    
	public void deletePost(final Integer id, User user) {
		Post post = postRepository.findById(id).get();
		//storageService.deleteFile(post.getImageName());
		System.out.println(storageService.deleteFile(post.getImageName()));
		postRepository.deleteById(id);
	}
	
    public PostDto getDtoFromPost(Post post) {
    	PostDto postDto = new PostDto();
    	postDto.setId(post.getId());
    	postDto.setImageUrl(post.getImageUrl());
    	postDto.setDescription(post.getDescription());
        return postDto;
    }

    public Post getPostFromDto(PostDto postDto, User user, MultipartFile image) {
    	Post post = new Post();
    	post.setImageName(System.currentTimeMillis() + "_" + image.getOriginalFilename());
    	post.setImageUrl(storageService.uploadFile(image, post.getImageName()));
    	post.setDescription(postDto.getDescription());
    	post.setUser(user);
        return post;
    }

}
