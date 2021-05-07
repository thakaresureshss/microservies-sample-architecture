package com.lib.common.security.utils;


import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.security.exceptions.InvalidJwtException;
import com.lib.common.utils.JsonUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenAuthenticationFilter extends GenericFilterBean {
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  JsonUtils jsonUtils;

  public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {
    String token = null;
    token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
    if (token == null) {
      filterChain.doFilter(req, res);
    } else {
      try {
        if (token != null && jwtTokenProvider.validateToken(token)) {
          Authentication auth = jwtTokenProvider.getAuthentication(token);
          if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
          }
        }
        filterChain.doFilter(req, res);
      } catch (final InvalidJwtException ex) {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        MovieBusinessError errorResp = new MovieBusinessError(ex.getMessage(), null);
        response.getOutputStream().write(jsonUtils.toJsonString(errorResp).getBytes());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        response.getOutputStream().flush();
      }
    }
  }
}

