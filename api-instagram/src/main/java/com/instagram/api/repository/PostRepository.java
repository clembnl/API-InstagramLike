package com.instagram.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.instagram.api.model.Post;
import com.instagram.api.model.User;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
	
	List<Post> findAllByUser(User user);

}
