package com.assigment.moviecatalog.client;

import com.lib.common.dto.MovieCastDto;
import java.util.List;
import java.util.UUID;

public interface CastClient {

  List<MovieCastDto> getCastDtoByMovieId(UUID movieId);

}
