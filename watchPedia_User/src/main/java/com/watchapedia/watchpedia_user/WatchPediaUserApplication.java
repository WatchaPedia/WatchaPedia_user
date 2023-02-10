package com.watchapedia.watchpedia_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @ConfigurationPropertiesScan을 쓰면  thymeleafconfig에 ConfigurationProperties("spring.thymeleaf3")를 스캔함
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class WatchPediaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchPediaUserApplication.class, args);
	}

}
