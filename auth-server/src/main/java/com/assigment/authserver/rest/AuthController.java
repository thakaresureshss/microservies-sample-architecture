package com.assigment.authserver.rest;

import com.lib.common.security.dto.AuthUser;
import com.lib.common.security.request.AuthRequest;
import com.lib.common.security.response.AuthResponse;
import com.lib.common.security.utils.JwtTokenProvider;
import com.lib.common.security.utils.UsersRepoUtils;
import io.swagger.annotations.Api;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is authentication provider class.
 * 
 * @author Suresh Thakare
 *
 */

@RestController
@RequestMapping("v1/")
@Api(tags = { "Authentication API's" })
public class AuthController {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @Autowired
  UsersRepoUtils userRepoUtil;

  @PostMapping("login")
  public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
    // log.debug("Signin user with: {}", authRequest);
    String username = authRequest.getUsername();
    String token = null;
    String refreshToken = null;
    Optional<AuthUser> userOptional = userRepoUtil.getUsers().stream()
        .filter(user -> user.getUsername().equalsIgnoreCase(authRequest.getUsername()))
        .findFirst();
    if (!userOptional.isPresent()) {
      throw new UsernameNotFoundException("Username: " + username + " not found");
    }
    token = tokenProvider.generateAuthenticationToken(username, userOptional.get().getRoles());
    refreshToken = tokenProvider.getRefreshToken(token);
    return ResponseEntity.ok(new AuthResponse(token, refreshToken));
  }
}
