package com.ll.ebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBooksApplication.class, args);
	}

}
