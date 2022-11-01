package com.example.demo;

import com.example.demo.Controller.ShowController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages= "com.example.demo")
public class Booking {

	@Autowired
	private ShowController showController;

	public static void main(String[] args) {
		SpringApplication.run(ShowController.class, args);
	}

}

