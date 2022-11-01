package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false"})
@RunWith( SpringJUnit4ClassRunner.class )
class BookingTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
    }
    @Test
    void whenContextLoads_thenRunnersAreNotLoaded() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(Booking.class),
                "CommandLineRunner should not be loaded during this integration test");
    }
}