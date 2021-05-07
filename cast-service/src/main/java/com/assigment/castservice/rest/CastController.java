package com.assigment.castservice.rest;

import com.assigment.castservice.service.impl.CastService;
import com.lib.common.dto.IdDto;
import com.lib.common.dto.MovieCastDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
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

@Api(tags = { "Cast API's" })
@RestController
@RequestMapping("v1")
public class CastController {

  @Autowired
  CastService castService;

  @ApiOperation(value = "Add cast details", response = IdDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 500, message = "Internal server error"),
      @ApiResponse(code = 409, message = "Conflict") })
  @PostMapping(value = "/movies/{movieId}/casts/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IdDto> addCast(
      @ApiParam(value = "Movie Id", required = true) @PathVariable("movieId") UUID movieId,
      @ApiParam(value = "Add Cast") @Valid @RequestBody MovieCastDto castDto,
      HttpServletRequest request) {
    // log.debug("Creating user with user details: {}", movieDto);
    castDto.setMovieId(movieId);
    return new ResponseEntity<>(new IdDto(castService.addCast(castDto).getId()),
        HttpStatus.CREATED);
  }

  @ApiOperation(value = "Get cast details", response = MovieCastDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 404, message = "Resource not found"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(value = "casts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieCastDto> getCastById(
      @ApiParam(value = "Get Cast", required = true) @PathVariable("id") UUID castId,
      HttpServletRequest request) {
    // log.debug("Getting user profile with user id: {}", id);
    return new ResponseEntity<>(castService.findCastById(castId), HttpStatus.OK);
  }

  @ApiOperation(value = "Get cast details", response = MovieCastDto.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 404, message = "Resource not found"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(value = "/movies/{movieId}/casts/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MovieCastDto>> getByMovieId(
      @ApiParam(value = "Movie Id", required = true) @PathVariable("movieId") UUID movieId,
      HttpServletRequest request) {
    // log.debug("Getting user profile with user id: {}", id);
    return new ResponseEntity<>(castService.findCastByMovieId(movieId), HttpStatus.OK);
  }

}
