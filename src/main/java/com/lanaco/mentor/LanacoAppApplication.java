package com.lanaco.mentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.lanaco.mentor.controllers.DestinationController;

@SpringBootApplication
public class LanacoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanacoAppApplication.class, args);
	}

}
