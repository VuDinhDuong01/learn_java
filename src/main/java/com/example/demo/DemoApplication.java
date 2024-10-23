package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	// build 
	// mvn clean package
	// java -jar target/name .jar.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
