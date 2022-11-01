package com.example.demo.Controller;

import com.example.demo.Exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@Slf4j
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@ComponentScan({"com.example.demo"})
@EntityScan(basePackages = "com.example.demo")
@EnableJpaRepositories(basePackages = "com.example.demo")
public class ShowController implements CommandLineRunner {

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private AdminController adminController;
    @Autowired
    private BuyerController buyerController;


    public void initialInput() throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        log.info("Key in role here:");
        while (true) {
            String input = scanner.nextLine();
            if (validateRole(input)) {
                break;
            }
        }
    }

    private boolean validateRole(String input) throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        if (input.equalsIgnoreCase("Admin")) {
            log.info("Hello {}, What do you want to do next?",input);
            adminController.flowForAdmin();
            return true;
        }
        if (input.equalsIgnoreCase("Buyer")) {
            log.info("Hello {}, What do you want to do next?",input);
            buyerController.flowForBuyer();
            return true;
        }
        log.info("Invalid Role Selected, Please try again!");
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        initialInput();
    }
}
