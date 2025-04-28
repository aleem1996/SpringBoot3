package com.org.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BlogSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSystemApplication.class, args);
	}

}
