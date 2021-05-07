package com.assigment.moviecatalog.service;

import com.lib.common.dto.MovieCatalogDto;
import java.util.UUID;

public interface MovieCatalogService {

  MovieCatalogDto getMovieCatalog(UUID catalogId);
}
