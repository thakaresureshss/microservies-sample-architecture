
package com.assigment.castservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * This class is security configuration class.
 * 
 * @author Suresh Thakare
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsService customUserDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowedHeaders("*");
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return super.userDetailsService();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().permitAll();
  }
}
