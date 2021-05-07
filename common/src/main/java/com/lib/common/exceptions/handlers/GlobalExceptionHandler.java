package com.lib.common.exceptions.handlers;

import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.exceptions.BadRequestException;
import com.lib.common.exceptions.MovieBusinessException;
import com.lib.common.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  ApplicationContext context;

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<MovieBusinessError> handleResourceNotFound(ResourceNotFoundException ex,
      WebRequest request) {
    log.debug("handling ResourceNotFoundException...");
    MovieBusinessError exception = new MovieBusinessError(ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<MovieBusinessError>(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<MovieBusinessError> handleBadRequest(BadRequestException ex,
      WebRequest request) {
    log.debug("handling BadRequestException...");
    MovieBusinessError exception = new MovieBusinessError(ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<MovieBusinessError>(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MovieBusinessException.class)
  public ResponseEntity<MovieBusinessError> handleMovieBusinessException(MovieBusinessException ex,
      WebRequest request) {
    log.error("Handling MovieBusinessException '{}'" + ex);
    MovieBusinessError error = ex.getError();
    return new ResponseEntity<MovieBusinessError>(error, error.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<MovieBusinessError> handleGlobalException(Exception ex,
      WebRequest request) {
    ex.printStackTrace();
    log.error("Handling Exception '{}'" + ex);
    MovieBusinessError exception = new MovieBusinessError(ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<MovieBusinessError>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
