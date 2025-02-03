package com.polling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.polling.model")
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories("com.polling.repository")
@SpringBootApplication
public class OnlinePollingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinePollingProjectApplication.class, args);
	}

}
