package com.assigment.castservice.utils;

import com.lib.common.dto.MovieCastDto;
import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.exceptions.MovieBusinessException;
import com.lib.common.exceptions.ResourceNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class CastRepository {
  private Set<MovieCastDto> castSet;

  public CastRepository() {
    super();
    this.castSet = new HashSet<MovieCastDto>();
  }

  public MovieCastDto addCast(MovieCastDto castDto) {
    castDto.setId(UUID.randomUUID());
    if (castSet.stream()
        .anyMatch(cast -> cast.getActorName().equalsIgnoreCase(castDto.getActorName()))) {
      MovieBusinessError error = new MovieBusinessError(HttpStatus.CONFLICT, "name.alreadyexits");
      throw new MovieBusinessException(error);
    }
    castSet.add(castDto);
    return castDto;
  }

  public Optional<MovieCastDto> getCast(UUID castId) {
    return Optional.of(castSet.stream().filter(cast -> cast.getId().equals(castId)).findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Cast ", "id", castId)));
  }

  public List<MovieCastDto> getCastsByMovieId(UUID movieId) {
    return castSet.stream().filter(cast -> cast.getMovieId().equals(movieId))
        .collect(Collectors.toList());
  }
}
