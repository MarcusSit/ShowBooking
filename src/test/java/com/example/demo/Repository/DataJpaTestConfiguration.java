package com.example.demo.Repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootConfiguration
@EnableJpaRepositories
@Slf4j
@EntityScan("com.example.demo.Model")
public class DataJpaTestConfiguration {

}
