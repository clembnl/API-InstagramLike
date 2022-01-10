package com.instagram.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.instagram.api.model.Commentary;
import com.instagram.api.model.Post;

@Repository
public interface CommentaryRepository extends CrudRepository<Commentary, Integer> {
	
	List<Commentary> findAllByPost(Post post);

}
