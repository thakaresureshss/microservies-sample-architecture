package com.lib.common.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  public JwtSecurityConfigurer(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and()
        .addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);
  }
}
