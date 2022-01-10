package com.instagram.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.config.MessageStrings;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.model.Token;
import com.instagram.api.model.User;
import com.instagram.api.repository.TokenRepository;
import com.instagram.api.util.Helper;

@Service
public class TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	
    public void saveConfirmationToken(Token token) {
        tokenRepository.save(token);
    }
	
    public User getUser(String token) {
        Token authenticationToken = tokenRepository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }
    
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }

    public Token getToken(User user) {
        return tokenRepository.findTokenByUser(user);
    }

}
