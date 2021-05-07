package com.assigment.zuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = { "com.lib.common", "com.assigment.zuulapigateway" })
@EnableEurekaClient 
@EnableZuulProxy // Enable Zuul

public class ZuulApigatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZuulApigatewayApplication.class, args);
  }

}
