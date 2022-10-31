package com.example.demo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@Slf4j
public class ShowBookingController {
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private ShowBookingService showBookingService;
    public boolean flow() {
        log.info("Key in role here:");
        while (true) {
            String input = getNextLine(scanner);
            if (validateRole(input)) {
                break;
            }
        }
        return false;
    }

    protected String getNextLine(Scanner scanner){
        return scanner.nextLine();
    }
    public boolean validateRole(String role) {
        if (role.equalsIgnoreCase("Admin")) {
            log.info("Hello {}, What do you want to do next?",role);
            flowForAdmin(getNextLine(scanner));
            return true;
        }
        if (role.equalsIgnoreCase("Buyer")) {
            log.info("Hello {}, What do you want to do next?",role);
            flowForBuyer(getNextLine(scanner));
            return true;
        }
        log.info("Invalid Role Selected, Please try again!");
        return false;
    }

    public void flowForAdmin(String input) {
        while (true) {
            var splitString = input.trim().split("\\s+");
            if (validateAdminAction(splitString)){
                break;
            }
        }
    }
    public void flowForBuyer(String input) {
        while (true) {
            var splitString = input.trim().split("\\s+");
            if (validateBuyerAction(splitString)){
                break;
            }
        }
    }

    public boolean validateAdminAction(String[] input) {
        if (input.length <= 5 && !input[0].isBlank()) {
            if (input[0].equalsIgnoreCase("Setup")&& input.length ==5){
                for (int i = 0; i < 5; i++) {
                    boolean isInputCorrect = checkIfEmpty(input[i]);
                    if (!isInputCorrect) {
                        log.info("Invalid setup, please key in an appropriate format: Setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
                        return false;
                    }
                }
                boolean check = checkForMaxLength(input[2], input[3]);
                if (check) {
                    return showBookingService.setUpAdmin(input);
                }
            } if (input[0].equalsIgnoreCase("View") && input.length ==2) {
                boolean checkIfEmpty = checkIfEmpty(input[1]);
                if (!checkIfEmpty){
                    log.info("Invalid view, please key in an appropriate format: View  <Show Number>");
                    return false;
                }
                return showBookingService.viewAdmin(input);
            }
            if (input[0].equalsIgnoreCase("Home")) {
                return flow();
            }
            log.info("Invalid input, please key in a valid command!");
            return false;
        }
        log.info("Invalid input, please key in a valid command!");
        return false;
    }

    public boolean checkForMaxLength(String row, String column) {
        if (Integer.parseInt(row) <= 26 && Integer.parseInt(column) <= 10) {
            return true;
        }
        if (Integer.parseInt(row) > 26){
            log.info("Max number of rows are 26!");
            return false;
        }
        if (Integer.parseInt(column) > 10){
            log.info("Max number of seats per row is 10!");
            return false;
        }
        return false;
    }

    public boolean checkIfEmpty(String string) {
        return !string.isBlank();
    }

    public boolean validateBuyerAction(String[] input) {
        if (input.length <= 4 && !input[0].isBlank()) {
            if (input[0].equalsIgnoreCase("Availability") && input.length ==2) {
                boolean checkIfEmpty = checkIfEmpty(input[1]);
                if (!checkIfEmpty){
                    log.info("Invalid view, please key in an appropriate format: Availability  <Show Number>");
                    return false;
                }
                return showBookingService.availabilityBuyer(input);
            }
            if (input[0].equalsIgnoreCase("Book")&& input.length ==4) {
                for (String s : input) {
                    if (!checkIfEmpty(s)) {
                        log.info("Invalid Booking, please key in an appropriate format: Book <Show Number> <Phone#> <Comma separated list of seats> ");
                        return false;
                    }
                }
                return showBookingService.bookBuyer(input);
            }
            if (input[0].equalsIgnoreCase("Cancel")&& input.length ==3) {
                for (String s : input) {
                    if (!checkIfEmpty(s)) {
                        log.info("Invalid Cancellation, please key in an appropriate format: Cancel  <Ticket#>  <Phone#> ");
                        return false;
                    }
                }
                return showBookingService.cancelBuyer(input);
            }
            if (input[0].equalsIgnoreCase("Home")) {
                return flow();
            }
            log.info("Invalid input, please key in a valid command!");
            return false;
        }
        log.info("Invalid input, please key in a valid command!");
        return false;
    }
}
