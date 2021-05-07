package com.assigment.movieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.assigment.movieservice","com.lib.common"})
public class MovieServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieServiceApplication.class, args);
  }

}
