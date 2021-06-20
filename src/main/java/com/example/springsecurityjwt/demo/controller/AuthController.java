package com.example.springsecurityjwt.demo.controller;

import com.example.springsecurityjwt.demo.controller.domain.AuthRequest;
import com.example.springsecurityjwt.demo.jwt.JwtTokenProvider;
import com.example.springsecurityjwt.demo.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  UserRepository userRepository;

  @PostMapping("/login")
  public String login(@RequestBody AuthRequest request) {
    String username = request.getUsername();
    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

    String token = jwtTokenProvider.createToken(username,
        userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
            .getRoles()
    );

    return token;

  }
}