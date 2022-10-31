package com.example.demo.Controller;

import com.example.demo.Exception.*;
import com.example.demo.Service.Impl.BuyerServiceImpl;
import com.example.demo.Utils.BookingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@Slf4j
public class BuyerController {

    @Autowired
    private BuyerServiceImpl buyerService;

    @Autowired
    private ShowController showController;

    private BookingUtils bookingUtils = new BookingUtils();
    private Scanner scanner = new Scanner(System.in);

    public void flowForBuyer() throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        while (true) {
            String buyerCommand = scanner.nextLine();
            var splitString = buyerCommand.trim().split("\\s+");
            if (validateBuyerAction(splitString)){
                break;
            }
        }
    }

    private boolean validateBuyerAction(String[] input) throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        if (input.length <= 4 && !input[0].isBlank()) {
            if (input[0].equalsIgnoreCase("Availability") && input.length ==2) {
                boolean checkIfEmpty = bookingUtils.checkIfEmpty(input[1]);
                if (!checkIfEmpty){
                    log.info("Invalid view, please key in an appropriate format: Availability  <Show Number>");
                    return false;
                }
                try {
                    long showNumber = Long.parseLong(input[1]);
                    var availableSeatNumbers = buyerService.availabilityOfShows(showNumber);
                    log.info("Available Seat Numbers: {}",availableSeatNumbers);
                    return false;
                } catch (ShowDoesNotExistException e){
                    log.info(e.getMessage());
                    return false;
                }
            }
            if (input[0].equalsIgnoreCase("Book")&& input.length ==4) {
                for (String s : input) {
                    if (!bookingUtils.checkIfEmpty(s)) {
                        log.info("Invalid Booking, please key in an appropriate format: Book <Show Number> <Phone#> <Comma separated list of seats> ");
                        return false;
                    }
                }
                long showNumber = Long.parseLong(input[1]);
                String phoneNumber = input[2];
                String seatNumbers = input[3];
                try {
                    buyerService.bookShows(showNumber,phoneNumber,seatNumbers);
                    return false;
                } catch (ShowDoesNotExistException |BookingExistsException | SeatAlreadyBookedException e){
                    log.info(e.getMessage());
                    return false;
                }
            }
            if (input[0].equalsIgnoreCase("Cancel")&& input.length ==3) {
                for (String s : input) {
                    if (!bookingUtils.checkIfEmpty(s)) {
                        log.info("Invalid Cancellation, please key in an appropriate format: Cancel  <Ticket#>  <Phone#> ");
                        return false;
                    }
                }
                long ticketNumber = Long.parseLong(input[1]);
                String phoneNumber = input[2];
                try{
                    var cancelledTicket = buyerService.cancelTicket(ticketNumber,phoneNumber);
                    log.info("Ticket {} has been cancelled. Have a nice day!",cancelledTicket);
                    return false;
                } catch (CancellationWindowExpiredException | TicketNotFoundException e){
                    log.info(e.getMessage());
                    return false;
                }
            }
            if (input[0].equalsIgnoreCase("Home")) {
                showController.initialInput();
            }
            log.info("Invalid input, please key in a valid command!");
            return false;
        }
        log.info("Invalid input, please key in a valid command!");
        return false;
    }

}
