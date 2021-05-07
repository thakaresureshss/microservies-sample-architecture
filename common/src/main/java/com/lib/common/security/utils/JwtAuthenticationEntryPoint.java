package com.lib.common.security.utils;

import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.utils.JsonUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Autowired
  JsonUtils jsonUtils;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    log.debug("Jwt authentication failed:" + authException);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    MovieBusinessError errorResp = new MovieBusinessError(HttpStatus.BAD_REQUEST,
        authException.getMessage());
    response.getOutputStream().write(jsonUtils.toJsonString(errorResp).getBytes());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    response.getOutputStream().flush();
  }

}
