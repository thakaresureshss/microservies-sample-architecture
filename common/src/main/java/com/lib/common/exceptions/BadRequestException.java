package com.lib.common.exceptions;


public class BadRequestException extends RuntimeException {

  private String message;

  public BadRequestException() {
    super();
  }

  public BadRequestException(String message) {
    super(message);
    this.message = message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


}
