package com.example.springsecurityjwt.demo.config;

import com.example.springsecurityjwt.demo.jwt.JwtAuthenticationEntryPoint;
import com.example.springsecurityjwt.demo.jwt.JwtTokenAuthenticationFilter;
import com.example.springsecurityjwt.demo.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtTokenProvider jwtTokenProvider;

  public JwtSecurityConfigurer(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtTokenAuthenticationFilter customFilter = new JwtTokenAuthenticationFilter(jwtTokenProvider);
    http.exceptionHandling()
        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        .and()
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}