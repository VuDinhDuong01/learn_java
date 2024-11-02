package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;

@SpringBootApplication
public class DemoApplication {

	  
	// build 
	// mvn clean package
	// java -jar target/name .jar.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
