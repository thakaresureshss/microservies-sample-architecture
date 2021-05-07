package com.assigment.moviecatalog.client;

import com.lib.common.dto.MovieDto;
import java.util.UUID;

public interface MovieClient {

  MovieDto getMovie(UUID movieId);

}
