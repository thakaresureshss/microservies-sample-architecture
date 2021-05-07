package com.assigment.movieservice.utils;

import com.lib.common.dto.MovieDto;
import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.exceptions.MovieBusinessException;
import com.lib.common.exceptions.ResourceNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MovieRepository {
  private Set<MovieDto> movieRepositorySet;

  public MovieRepository() {
    super();
    this.movieRepositorySet = new HashSet<MovieDto>();
  }

  public MovieDto addMovie(MovieDto movieDto) {
    movieDto.setId(UUID.randomUUID());
    if (movieRepositorySet.stream()
        .anyMatch(movie -> movie.getName().equalsIgnoreCase(movieDto.getName()))) {
      MovieBusinessError error = new MovieBusinessError(HttpStatus.CONFLICT, "name.alreadyexits");
      throw new MovieBusinessException(error);
    }
    movieRepositorySet.add(movieDto);
    return movieDto;
  }

  public Optional<MovieDto> getMovieById(UUID movieId) {
    return Optional
        .of(movieRepositorySet.stream().filter(movie -> movie.getId().equals(movieId)).findFirst())
        .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
  }
}
