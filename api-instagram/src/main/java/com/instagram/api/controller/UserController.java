package com.instagram.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.api.dto.ResponseDto;
import com.instagram.api.dto.user.SignInDto;
import com.instagram.api.dto.user.SignInResponseDto;
import com.instagram.api.dto.user.SignUpDto;
import com.instagram.api.exception.AuthenticationFailException;
import com.instagram.api.exception.CustomException;
import com.instagram.api.model.User;
import com.instagram.api.repository.UserRepository;
import com.instagram.api.service.TokenService;
import com.instagram.api.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	UserService userService;
	
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;
    
    @GetMapping("/all")
    public List<User> findAllUser() throws AuthenticationFailException {
        Iterable<User> iterable = userRepository.findAll();
        List<User> users = StreamSupport.stream(iterable.spliterator(), false)
        					.collect(Collectors.toList());
        return users;
    }

    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignUpDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    //TODO token should be updated
    @PostMapping("/signIn")
    public SignInResponseDto SignIn(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }
    
    

}
