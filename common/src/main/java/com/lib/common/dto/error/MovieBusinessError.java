package com.lib.common.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


/**
 * This class will be used to handled business related exceptions.
 * 
 * @author Suresh Thakare
 *
 */

@Setter
@Getter
public class MovieBusinessError {
  private static final long serialVersionUID = 1L;
  private HttpStatus status;
  private String code;
  private String message;
  private String debugMessage;
  private String description;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
  private List<ValidationError> voilations;

  public MovieBusinessError() {
    timestamp = LocalDateTime.now();
  }

  public MovieBusinessError(HttpStatus status) {
    this();
    this.status = status;
  }

  public MovieBusinessError(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }


  public MovieBusinessError(HttpStatus status, String code, Throwable ex) {
    this();
    this.status = status;
    this.code = code;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public MovieBusinessError(HttpStatus status, String code, String message) {
    this();
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public MovieBusinessError(HttpStatus status, String code) {
    this();
    this.status = status;
    this.code = code;
  }

  public MovieBusinessError(HttpStatus status, List<ValidationError> voilations) {
    super();
    this.status = status;
    this.voilations = voilations;
  }

  public MovieBusinessError(String message, String description) {
    this();
    this.message = message;
    this.description = description;
  }

}
