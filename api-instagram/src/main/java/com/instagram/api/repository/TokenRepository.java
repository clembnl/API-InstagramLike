package com.instagram.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.instagram.api.model.Token;
import com.instagram.api.model.User;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
	Token findTokenByUser(User user);
	Token findTokenByToken(String token);
}
