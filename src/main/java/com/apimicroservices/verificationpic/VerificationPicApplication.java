package com.apimicroservices.verificationpic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class VerificationPicApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerificationPicApplication.class, args);
	}
}
