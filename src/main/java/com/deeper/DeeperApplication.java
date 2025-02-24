package com.deeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class DeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeeperApplication.class, args);
	}

}
