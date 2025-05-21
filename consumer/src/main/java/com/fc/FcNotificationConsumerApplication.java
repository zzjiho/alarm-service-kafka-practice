package com.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fc.consumer", "com.fc"})
public class FcNotificationConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcNotificationConsumerApplication.class, args);
	}

}
