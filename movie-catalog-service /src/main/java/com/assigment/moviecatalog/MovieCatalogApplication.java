package com.assigment.moviecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.assigment.moviecatalog","com.lib.common"})
@EnableEurekaClient
public class MovieCatalogApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieCatalogApplication.class, args);
  }

}
