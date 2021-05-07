package com.assigment.movieservice.rest;

import com.assigment.movieservice.service.MovieService;
import com.lib.common.dto.IdDto;
import com.lib.common.dto.MovieDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class MovieController {

  @Autowired
  MovieService movieService;

  @ApiOperation(value = "Add Movie", response = IdDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 500, message = "Internal server error"),
      @ApiResponse(code = 409, message = "Conflict") })
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IdDto> addMovie(
      @ApiParam(value = "Add Movie") @Valid @RequestBody MovieDto movieDto,
      HttpServletRequest request) {
    // log.debug("Creating user with user details: {}", movieDto);
    return new ResponseEntity<>(new IdDto(movieService.addMovie(movieDto).getId()),
        HttpStatus.CREATED);
  }
  

  @ApiOperation(value = "Get Movie", response = MovieDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 404, message = "Resource not found"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieDto> getMovieById(
      @ApiParam(value = "Get Movie", required = true) @PathVariable("id") UUID movieId,
      HttpServletRequest request) {
    // log.debug("Getting user profile with user id: {}", id);
    return new ResponseEntity<>(movieService.findByMovieId(movieId), HttpStatus.OK);
  }

}
