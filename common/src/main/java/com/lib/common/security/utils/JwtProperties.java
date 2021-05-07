package com.lib.common.security.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

  @Value("${jwt.token.secret:secret}")
  private String secretKey;

  @Value("${jwt.token.validity:300000}")
  private long validityInMs;

  @Value("${jwt.token.refresh.validity:18000000}")
  private long refreshValidityInMs;

}
