package com.assigment.castservice.service.impl;

import com.lib.common.dto.MovieCastDto;
import java.util.List;
import java.util.UUID;

public interface CastService {

  MovieCastDto addCast(MovieCastDto castDto);

  MovieCastDto findCastById(UUID castId);

  List<MovieCastDto> findCastByMovieId(UUID movieId);

}
