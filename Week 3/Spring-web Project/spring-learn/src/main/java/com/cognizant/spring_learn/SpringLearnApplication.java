package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) {
		log.info(">>> Entering SpringLearnApplication.main() - bootstrapping Spring context...");

		ConfigurableApplicationContext context = SpringApplication.run(SpringLearnApplication.class, args);

		log.info(">>> SpringLearnApplication started successfully. Active bean count: {}",
				context.getBeanDefinitionCount());
	}

}