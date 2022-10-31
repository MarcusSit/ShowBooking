package com.example.demo;

import com.example.demo.Controller.ShowController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
@ComponentScan({"com.example.demo","com.example.demo.Model","com.example.demo.Repository","com.example.demo.Controller"})
@EntityScan(basePackages = {"com.example.demo.Model"})
@EnableJpaRepositories(basePackages = {"com.example.demo.Repository"})
@Profile("!test")
public class Booking implements CommandLineRunner {

	@Autowired
	private ShowController showController;

	public static void main(String[] args) {
		SpringApplication.run(Booking.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showController.initialInput();
	}

}

