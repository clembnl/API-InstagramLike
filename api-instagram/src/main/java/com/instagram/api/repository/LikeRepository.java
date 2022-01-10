package com.instagram.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.instagram.api.model.Like;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;

@Repository
public interface LikeRepository extends CrudRepository<Like, Integer> {
	
	List<Like> findAllByPost(Post post);

}
