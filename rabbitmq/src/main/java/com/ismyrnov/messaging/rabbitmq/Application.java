package com.ismyrnov.messaging.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@EnableBinding({Source.class, CustomSink.class})*/
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
