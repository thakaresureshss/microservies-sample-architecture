package com.lib.common.security.service;

import com.lib.common.security.dto.AuthUser;
import com.lib.common.security.utils.UsersRepoUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UsersRepoUtils userRepoUtils;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    for (AuthUser user : userRepoUtils.getUsers()) {
      if (user.getUsername().equals(username)) {
        return new User(user.getUsername(), user.getPassword(),
            getGrantedAuthorities(
                user.getRoles()));
      }
    }
    throw new UsernameNotFoundException("Username: " + username + " not found");
  }

  public Collection<? extends GrantedAuthority> getAuthorities(AuthUser user) {
    return getGrantedAuthorities(
        user.getRoles().stream().map(role -> role).collect(Collectors.toList()));
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
}
