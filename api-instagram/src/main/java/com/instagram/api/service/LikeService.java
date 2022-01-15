package com.instagram.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.dto.like.LikeDto;
import com.instagram.api.model.Like;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.repository.LikeRepository;

@Service
public class LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	public List<LikeDto> listLikesByPost(Post post) {
		List<Like> likeList = likeRepository.findAllByPost(post);
        List<LikeDto> likes = new ArrayList<>();
        for (Like like:likeList){
            LikeDto likeDto = getDtoFromLike(like);
            likes.add(likeDto);
        }
        return likes;
	}
	
	public void deleteLike(final Integer id, User user) {
		likeRepository.deleteById(id);
	}
	
    public void addLike(LikeDto likeDto, User user, Post post) {
        Like like = getLikeFromDto(likeDto, user, post);
        likeRepository.save(like);
    }
	
    public static LikeDto getDtoFromLike(Like like) {
        LikeDto likeDto = new LikeDto(like);
        return likeDto;
    }
    
    public static Like getLikeFromDto(LikeDto likeDto, User user, Post post) {
        Like like = new Like(likeDto, user, post);
        return like;
    }

}
