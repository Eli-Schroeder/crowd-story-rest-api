package com.storyblocks.storyblocksservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StoryblocksServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryblocksServiceApplication.class, args);
	}

}
