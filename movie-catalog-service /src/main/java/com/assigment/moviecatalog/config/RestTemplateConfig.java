
package com.assigment.moviecatalog.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  /*
   * @Bean RequestAuthorizationInterceptor authorizationInterceptor() { return new
   * RequestAuthorizationInterceptor(); }
   */

  @Bean
  // @LoadBalanced
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    RestTemplate restTemplate = builder.build();
    /*
     * List<ClientHttpRequestInterceptor> interceptors =
     * restTemplate.getInterceptors(); if (CollectionUtils.isEmpty(interceptors)) {
     * interceptors = new ArrayList<>(); }
     * interceptors.add(authorizationInterceptor());
     * restTemplate.setInterceptors(interceptors);
     */
    return restTemplate;
  }
}
