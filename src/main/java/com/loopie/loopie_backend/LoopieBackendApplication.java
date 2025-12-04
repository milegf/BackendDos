package com.loopie.loopie_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.loopie")
@ComponentScan(basePackages = "com.loopie")
@EntityScan(basePackages = "com.loopie")
@EnableJpaRepositories(basePackages = "com.loopie")
public class LoopieBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoopieBackendApplication.class, args);
	}

}
