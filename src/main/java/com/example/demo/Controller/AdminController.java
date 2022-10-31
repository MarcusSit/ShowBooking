package com.example.demo.Controller;

import com.example.demo.Exception.*;
import com.example.demo.Model.ShowSetup;
import com.example.demo.Service.Impl.AdminServiceImpl;
import com.example.demo.Utils.BookingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private ShowController showController;

    private BookingUtils bookingUtils = new BookingUtils();

    @Autowired
    private AdminServiceImpl adminService;

    private Scanner scanner = new Scanner(System.in);

    public void flowForAdmin() throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        while (true) {
            String adminCommand = scanner.nextLine();
            var splitString = adminCommand.trim().split("\\s+");
            if (validateAdminAction(splitString)){
                break;
            }
        }
    }

    private boolean validateAdminAction(String[] input) throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException, TicketNotFoundException, CancellationWindowExpiredException {
        if (input.length <= 5 && !input[0].isBlank()) {
            if (input[0].equalsIgnoreCase("Setup")&& input.length ==5){
                for (int i = 0; i < 5; i++) {
                    boolean isInputCorrect = bookingUtils.checkIfEmpty(input[i]);
                    if (!isInputCorrect) {
                        log.info("Invalid setup, please key in an appropriate format: Setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
                        return false;
                    }
                }
                long showNumber = Long.parseLong(input[1]);
                int numberOfRows = Integer.parseInt(input[2]);
                int numberOfSeatsPerRow = Integer.parseInt(input[3]);
                long cancellationWindow = Long.parseLong(input[4]);
                boolean check = bookingUtils.checkForMaxLength(numberOfRows, numberOfSeatsPerRow);
                if (check) {
                    adminService.setup(showNumber,numberOfRows,numberOfSeatsPerRow,cancellationWindow);
                    log.info("Show has been setup! Admin, Please enter a new command!");
                    return false;
                }
            } if (input[0].equalsIgnoreCase("View") && input.length ==2) {
                boolean checkIfEmpty = bookingUtils.checkIfEmpty(input[1]);
                if (!checkIfEmpty){
                    log.info("Invalid view, please key in an appropriate format: View  <Show Number>");
                    return false;
                }
                try{
                    long showNumber = Long.parseLong(input[1]);
                    List<ShowSetup> showSetups = adminService.view(showNumber);
                    for (ShowSetup showSetup : showSetups) {
                        String showNumber1 = showSetup.getShowNumber().toString();
                        String ticketNumber = showSetup.getBookingId().toString();
                        String buyerPhone = showSetup.getPhoneNumber();
                        String seatNumbers = showSetup.getSeatNumber();
                        if (buyerPhone == null) {
                            buyerPhone = "Not Booked Yet!";
                        }
                        log.info(showNumber1 + " " + ticketNumber + " " + buyerPhone + " " + seatNumbers);
                    }
                    return false;
                } catch (ShowDoesNotExistException e) {
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
