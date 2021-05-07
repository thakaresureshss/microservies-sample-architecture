package com.assigment.castservice.service.impl;

import com.assigment.castservice.utils.CastRepository;
import com.lib.common.dto.MovieCastDto;
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
public class CastServiceImpl implements CastService {

  @Autowired
  CastRepository castRepository;

  @Override
  public MovieCastDto addCast(MovieCastDto castDto) {
    validateRequest(castDto);
    return castRepository.addCast(castDto);
    // This call can be replaced by database repository
  }

  /**
   * This Method validate all business validation against request submitted for
   * add cast information
   * 
   * @param castDto
   */
  private void validateRequest(MovieCastDto castDto) {
    // Validate Request here
    List<ValidationError> validationErrors = new ArrayList();
    if (StringUtils.isBlank(castDto.getActorName())) {
      validationErrors
          .add(new ValidationError("actorName", castDto.getActorName(), "Acto name required"));
    }
    if (!CollectionUtils.isEmpty(validationErrors)) {
      throw new MovieBusinessException(
          new MovieBusinessError(HttpStatus.BAD_REQUEST, validationErrors));
    }
  }

  /**
   * This method will find cast details based on id if it presents in in memory
   * store, If not present will throw runtime exception ResourceNotFound
   * 
   * @param castId
   */

  @Override
  public MovieCastDto findCastById(UUID castId) {
    // This call can be replaced by database repository
    Optional<MovieCastDto> castOptional = castRepository.getCast(castId);
    if (castOptional.isPresent()) {
      return castOptional.get();
    }
    return null;
  }

  @Override
  public List<MovieCastDto> findCastByMovieId(UUID movieId) {
    return castRepository.getCastsByMovieId(movieId);
  }
}
