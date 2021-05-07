package com.lib.common.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class act as response for JWT tokens.
 * 
 * @author Suresh Thakare
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private String refreshToken;

  public AuthResponse(String accessToken,String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
