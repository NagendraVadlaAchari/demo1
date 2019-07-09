package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoSpringBoot1Application {

	
	@GetMapping("/Name/{name}")
	public String getName1(@PathVariable String name) {
		System.out.println("getName api is called {}" + name);
	return name;	
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBoot1Application.class, args);
	}

}
