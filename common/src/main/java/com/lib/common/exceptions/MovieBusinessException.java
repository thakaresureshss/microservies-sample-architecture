package com.lib.common.exceptions;

import com.lib.common.dto.error.MovieBusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class responsibility to hold all business validation errors
 * 
 * @author Suresh Thakare
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieBusinessException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String language;
  private MovieBusinessError error;

  public MovieBusinessException(MovieBusinessError error) {
    super();
    this.error = error;
  }

}
