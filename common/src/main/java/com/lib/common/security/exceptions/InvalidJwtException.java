package com.lib.common.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * This is custom class created to handled exception occurred while authenticating token.
 * 
 * @author Suresh Thakare
 *
 */
public class InvalidJwtException extends AuthenticationException {
  private static final long serialVersionUID = -761503632186596342L;

  public InvalidJwtException(String e) {
    super(e);
  }
}

