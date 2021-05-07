package com.assigment.castservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.assigment.castservice","com.lib.common"})
public class CastServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CastServiceApplication.class, args);
	}

}
