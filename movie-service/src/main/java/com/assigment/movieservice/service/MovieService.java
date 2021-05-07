package com.assigment.movieservice.service;

import com.lib.common.dto.MovieDto;
import java.util.UUID;

public interface MovieService {

  MovieDto addMovie(MovieDto movieDto);

  MovieDto findByMovieId(UUID id);

}
