package com.assigment.moviecatalog.service.impl;

import com.assigment.moviecatalog.client.CastClient;
import com.assigment.moviecatalog.client.MovieClient;
import com.assigment.moviecatalog.service.MovieCatalogService;
import com.lib.common.dto.MovieCastDto;
import com.lib.common.dto.MovieCatalogDto;
import com.lib.common.dto.MovieDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MovieCatalogServiceImpl implements MovieCatalogService {

  @Autowired
  MovieClient movieClient;

  @Autowired
  CastClient castClient;

  @Autowired
  ModelMapper mapper;

  @Override
  public MovieCatalogDto getMovieCatalog(UUID catalogId) {
    MovieCatalogDto movieCatalogDto = new MovieCatalogDto();
    MovieDto movie = movieClient.getMovie(catalogId);
    if (movie != null) {
      movieCatalogDto = mapper.map(movie, MovieCatalogDto.class);
    }
    List<MovieCastDto> castList = castClient.getCastDtoByMovieId(catalogId);
    if (!CollectionUtils.isEmpty(castList)) {
      movieCatalogDto.setCasts(
          castList.stream().map(cast -> cast.getActorName()).collect(Collectors.toList()));
    }
    return movieCatalogDto;
  }
}
