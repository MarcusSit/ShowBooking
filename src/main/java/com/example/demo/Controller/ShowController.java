package com.example.demo.Controller;

import com.example.demo.Exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@Slf4j
public class ShowController {

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
}
