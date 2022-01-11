package com.instagram.api.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.config.MessageStrings;
import com.instagram.api.config.ResponseStatus;
import com.instagram.api.dto.ResponseDto;
import com.instagram.api.dto.user.SignInDto;
import com.instagram.api.dto.user.SignInResponseDto;
import com.instagram.api.dto.user.SignUpDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.CustomException;
import com.instagram.api.model.Token;
import com.instagram.api.model.User;
import com.instagram.api.repository.UserRepository;
import com.instagram.api.util.Helper;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
    @Autowired
    TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseDto signUp(SignUpDto signupDto)  throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // first encrypt the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(signupDto.getUsername(), signupDto.getEmail(), encryptedPassword );

        User createdUser;
        try {
            // save the User
            createdUser = userRepository.save(user);
            // generate token for user
            final Token token = new Token(createdUser);
            // save token in database
            tokenService.saveConfirmationToken(token);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }
    
    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Helper.notNull(user)){
            throw  new AuthenticationFailException("user not present");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passowrd doesnot match
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        Token token = tokenService.getToken(user);

        if(!Helper.notNull(token)) {
            // token not present
            throw new CustomException("token not present");
        }

        return new SignInResponseDto ("success", token.getToken());
    }
    
    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

}
