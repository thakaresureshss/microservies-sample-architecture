package com.lib.common.security.dto;

import java.util.List;
import lombok.Data;

@Data
public class AuthUser {
  private Integer id;
  private String username, password;
  private List<String> roles;

  public AuthUser(Integer id, String username, String password, List<String> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}