package com.assigment.moviecatalog.rest;

import com.assigment.moviecatalog.service.MovieCatalogService;
import com.lib.common.dto.MovieDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class acts as a controller to handle request related to User.
 * 
 * @author suresh.thakare
 */

@Api(tags = { "Movie API's" })
@RestController
@RequestMapping("v1/movies")
public class MovieCatalogController {

  @Autowired
  MovieCatalogService movieCatalogService;

  @ApiOperation(value = "Get movie catalog", response = MovieDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 404, message = "Resource not found"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieDto> getMovieById(
      @ApiParam(value = "Get movie catalog by id", required = true) @PathVariable("id") UUID catalogId,
      HttpServletRequest request) {
    // log.debug("Getting user profile with user id: {}", id);
    return new ResponseEntity<>(movieCatalogService.getMovieCatalog(catalogId), HttpStatus.OK);
  }

}
