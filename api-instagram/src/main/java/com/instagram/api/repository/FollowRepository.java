package com.instagram.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram.api.model.Follow;
import com.instagram.api.model.User;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Integer> {
	
	@Query("from Follow where following=:user")
	List<Follow> getFollowersByUser(@Param("user") User user);
	
	@Query("from Follow where follower=:user")
	List<Follow> getFollowingsByUser(@Param("user") User user);

}
