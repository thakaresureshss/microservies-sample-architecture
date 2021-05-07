package com.assigment.moviecatalog.client.impl;

import com.assigment.moviecatalog.client.MovieClient;
import com.lib.common.dto.MovieDto;
import com.lib.common.dto.error.MovieBusinessError;
import com.lib.common.exceptions.MovieBusinessException;
import com.lib.common.utils.JsonUtils;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieClientImpl implements MovieClient {
  private final Logger log = LoggerFactory.getLogger(MovieClientImpl.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private JsonUtils jsonUtils;

  public MovieClientImpl(RestTemplate restTemplate, JsonUtils jsonUtils) {
    super();
    this.restTemplate = restTemplate;
    this.jsonUtils = jsonUtils;
  }

  public MovieClientImpl() {
    super();
  }

  private String getUrl() {
    return String.format("http://localhost:8081/v1/movies/");
  }

  @Override
  public MovieDto getMovie(UUID movieId) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> request = new HttpEntity<>(headers);
    String url = getUrl() + "/" + movieId;
    try {
      return restTemplate.exchange(url, HttpMethod.GET, request, MovieDto.class).getBody();
    } catch (HttpStatusCodeException ex) {
      log.error("[** CLIENT ERROR **  getting Movie details from cast service  !!! ] {}", ex);
      String responseStr = ex.getResponseBodyAsString();
      try {
        MovieBusinessError movieBusinessError = jsonUtils.fromJsonStringToError(responseStr,
            MovieBusinessError.class);
        throw new MovieBusinessException(movieBusinessError);
      } catch (IOException e1) {
        throw new MovieBusinessException(new MovieBusinessError(ex.getStatusCode(), "4036"));
        // Define proper code here
      }
    } catch (Exception e) {
      log.info("[** ERROR **  getting Movie details from cast service  !!! ] {}", e);
      throw new MovieBusinessException(
          new MovieBusinessError(HttpStatus.INTERNAL_SERVER_ERROR, "500"));
    }
  }
}