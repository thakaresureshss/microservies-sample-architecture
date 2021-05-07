package com.assigment.movieservice.service.impl;

import com.assigment.movieservice.service.MovieService;
import com.assigment.movieservice.utils.MovieRepository;
import com.lib.common.dto.MovieDto;
import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.dto.error.ValidationError;
import com.lib.common.exceptions.MovieBusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  MovieRepository movieRepository;

  @Override
  public MovieDto addMovie(MovieDto movieDto) {
    validateRequest(movieDto);
    return movieRepository.addMovie(movieDto); // This call can be replaced by database repository
  }

  /**
   * This Method validate all business validation against request
   * 
   * @param movieDto
   */
  private void validateRequest(MovieDto movieDto) {
    // Validate Request here
    List<ValidationError> validationErrors = new ArrayList();
    if (CollectionUtils.isEmpty(movieDto.getDirectors())) {
      validationErrors.add(new ValidationError("directors", movieDto.getDirectors(),
          "Atleast one director should required"));
    }
    if (CollectionUtils.isEmpty(movieDto.getLangugage())) {
      validationErrors.add(new ValidationError("langugage", movieDto.getLangugage(),
          "Atleast one language should required"));
    }
    if (StringUtils.isBlank(movieDto.getName())) {
      validationErrors.add(new ValidationError("name", movieDto.getName(), "Movie name required"));
    }
    if (!CollectionUtils.isEmpty(validationErrors)) {
      throw new MovieBusinessException(
          new MovieBusinessError(HttpStatus.BAD_REQUEST, validationErrors));
    }
  }

  @Override
  public MovieDto findByMovieId(UUID movieId) {
    // This call can be replaced by database repository
    Optional<MovieDto> movieOptional = movieRepository.getMovieById(movieId);
    if (movieOptional.isPresent()) {
      return movieOptional.get();
    }
    return null;
  }
}
