package com.example.springsecurityjwt.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class UserRepository {

  private static final Map<String, User> allUsers = new HashMap<>();

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  protected void init() {
    allUsers.put("admin", new User("admin", passwordEncoder.encode("password"), Collections.singletonList("ROLE_ADMIN")));
    allUsers.put("user", new User("user", passwordEncoder.encode("password"), Collections.singletonList("ROLE_USER")));
  }

  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(allUsers.get(username));
  }
}