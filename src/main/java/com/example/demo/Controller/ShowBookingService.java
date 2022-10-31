package com.example.demo.Controller;

import com.example.demo.Model.ShowSetupModel;
import com.example.demo.Model.UpdateShowModel;
import com.example.demo.Repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ShowBookingService {

    @Autowired
    ShowRepository showRepository;
    private List<ShowSetupModel> showSetupModelList = new ArrayList<>();

    public boolean setUpAdmin(String[] input) {
        int row = Integer.parseInt(input[2]);
        int column = Integer.parseInt(input[3]);
        List<Integer> bookedRows = new ArrayList<>();
        List<Integer> bookedColumns = new ArrayList<>();
        System.out.println("Show created with following seats:");
        showInConsole(column, row, bookedRows, bookedColumns);
        for (int i = 1; i <= row; i++) {
            String alphabetFromInt = String.valueOf((char) (i + 'A' - 1));
            for (int j = 1; j <= column; j++) {
                saveSeatNumber(input, alphabetFromInt, String.valueOf(j));
            }
        }
        showRepository.saveAll(showSetupModelList);
        log.info("Setup Done! Admin, Please enter a new command!");
        return false;
    }

    private void saveSeatNumber(String[] input, String rowAlphabet, String column) {
        ShowSetupModel showSetupModel = new ShowSetupModel();
        showSetupModel.setShowNumber(Long.parseLong(input[1]));
        showSetupModel.setSeatNumber((rowAlphabet + column).trim());
        showSetupModel.setCancellationWindow(Long.parseLong(input[4]));
        showSetupModel.setNumberOfRows(Long.parseLong(input[2]));
        showSetupModel.setNumberOfSeatsPerRow(Long.parseLong(input[3]));
        showSetupModel.setAvailability("Available");
        System.out.println(showSetupModel);
        showSetupModelList.add(showSetupModel);
    }

    private void showInConsole(int column, int row, List<Integer> bookedRow, List<Integer> bookedColumn) {
        System.out.print("  ");
        for (int i = 1; i <= column; i++) {
            System.out.print(i + " ");
        }
        //has some issue with Availability after booking with 2D Array
        System.out.println();
        for (int i = 1; i <= row; i++) {
            String alphabetFromInt = String.valueOf((char) (i + 'A' - 1));
            System.out.print(alphabetFromInt + " ");
            for (int j = 0; j < column; j++) {
                if (bookedRow.size() > 0 && bookedColumn.size() > 0) {
                    for (int bk = 0; bk < bookedRow.size(); bk++) {
                        for (int bc = 0; bc < bookedColumn.size(); bc++) {
                            if ((bookedRow.get(bk) == i) && (bookedColumn.get(bc) == j + 1)) {
                                System.out.print('X' + " ");
                            } else
                                System.out.print('A' + " ");
                        }
                    }
                }
                System.out.print('A' + " ");
            }
            System.out.println();
        }
    }

    public boolean viewAdmin(String[] input) {
        Optional<List<ShowSetupModel>> optionalShowSetupModelList = showRepository.findByShowNumber(Long.valueOf(input[1]));
        System.out.println("Show Number " + "Ticket Number " + "Buyer Phone Number " + "Seat Number");
        if (optionalShowSetupModelList.isPresent()) {
            List<ShowSetupModel> showSetupModels = optionalShowSetupModelList.get();
            for (ShowSetupModel showSetupModel : showSetupModels) {
                String showNumber = showSetupModel.getShowNumber().toString();
                String ticketNumber = showSetupModel.getBookingId().toString();
                String buyerPhone = showSetupModel.getPhoneNumber();
                String seatNumbers = showSetupModel.getSeatNumber();
                if (buyerPhone == null) {
                    buyerPhone = "Not Booked Yet!";
                }
                System.out.println(showNumber + " " + ticketNumber + " " + buyerPhone + " " + seatNumbers);
            }
        }
        log.info("View Done! Admin, Please enter a new command!");
        return false;
    }

    public boolean availabilityBuyer(String[] input) {
        Optional<List<ShowSetupModel>> optionalShowSetupModelList = showRepository.findByShowNumber(Long.valueOf(input[1]));
        if (optionalShowSetupModelList.isPresent()) {
            List<Integer> rowNumbers = new ArrayList<>();
            List<Integer> columnNumbers = new ArrayList<>();
            List<ShowSetupModel> showSetupModels = optionalShowSetupModelList.get();
            List<String> availableSeatNumbers = new ArrayList<>();
            for (ShowSetupModel showSetupModel : showSetupModels) {
                String availabilty = showSetupModel.getAvailability();
                int rowNumber = (showSetupModel.getSeatNumber().charAt(0) - 64);
                int columnNumber = Integer.parseInt(showSetupModel.getSeatNumber().substring(1));
                if (availabilty.equalsIgnoreCase("Booked")) {
                    rowNumbers.add(rowNumber);
                    columnNumbers.add(columnNumber);
                } else {
                    availableSeatNumbers.add(showSetupModel.getSeatNumber());
                }
            }
            log.info("Available Seat Numbers: {}",availableSeatNumbers);
            showInConsole(Math.toIntExact(showSetupModels.get(0).getNumberOfRows()), Math.toIntExact(showSetupModels.get(0).getNumberOfSeatsPerRow()), rowNumbers, columnNumbers);
        }
        log.info("Availability Done! Buyer, Please enter a new command!");
        return false;
    }

    public boolean bookBuyer(String[] input) {
        String showNumber = input[1];
        String phoneNumber = input[2];
        String seats = input[3];
        List<String> listOfSeats = Arrays.asList(seats.split(","));
        if (validation(phoneNumber,showNumber,listOfSeats)){
            return false;
        }
        return false;
    }

    public boolean cancelBuyer(String[] input) {
        String ticketNumber = input[1];
        String phoneNumber = input[2];
        Optional<ShowSetupModel> findByTicketNumber = showRepository.findByIdAndPhoneNumber(Long.valueOf((ticketNumber)),phoneNumber);
        if (findByTicketNumber.isPresent()) {
            ShowSetupModel showSetupModel = findByTicketNumber.get();
            System.out.println(showSetupModel.getBookingTime());
            if (calculateDifferenceInTime(showSetupModel.getBookingTime(),Timestamp.valueOf(LocalDateTime.now())) < showSetupModel.getCancellationWindow()) {
                UpdateShowModel updateShowModel = new UpdateShowModel();
                updateShowModel.setSeatNumber(showSetupModel.getSeatNumber());
                updateShowModel.setShowNumber(showSetupModel.getShowNumber());
                updateShowModel.setPhoneNumber("");
                updateShowModel.setAvailability("Available");
                updateShowModel.setBookingTime(null);
                showRepository.updateBooking(updateShowModel.getPhoneNumber(),updateShowModel.getBookingTime(),
                        updateShowModel.getAvailability(), updateShowModel.getSeatNumber(),updateShowModel.getShowNumber().toString());
                log.info("Ticket: {} for Show: {} has been Cancelled. Have a nice day!",showSetupModel.getBookingId().toString(),updateShowModel.getShowNumber());
            } else {
                log.info("Time window for Cancellation of ticket Id: {} has expired. Sorry!",showSetupModel.getBookingId());
                return false;
            }
        }
        return false;
    }

    private long calculateDifferenceInTime(Timestamp bookingTime, Timestamp cancellationTime) {
        return bookingTime.getTime() - cancellationTime.getTime();
    }
    private boolean validation(String phoneNumber,String showNumber,List<String> listOfSeats) {
        Optional<List<ShowSetupModel>> optionalShowSetupModelList = showRepository.findByShowNumber(Long.valueOf(showNumber));
        if (optionalShowSetupModelList.isPresent()) {
            List<ShowSetupModel> showSetupModels = optionalShowSetupModelList.get();
            for (ShowSetupModel showSetupModel : showSetupModels) {
                if (!StringUtils.isNullOrEmpty(showSetupModel.getPhoneNumber())) {
                    if (showSetupModel.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                        System.out.println("PhoneNumber already has a booking for this show. Only 1 booking per PhoneNumber is allowed!");
                        return false;
                    }
                }
                for (String seatNumber: listOfSeats) {
                    if (showSetupModel.getSeatNumber().equals(seatNumber)) {
                        if (showSetupModel.getAvailability().equalsIgnoreCase("Booked")) {
                            log.info("Seat {} has already been booked! Please book another seat!", seatNumber);
                            return false;
                        }
                        UpdateShowModel updateShowModel = new UpdateShowModel();
                        updateShowModel.setSeatNumber(seatNumber);
                        updateShowModel.setShowNumber(Long.valueOf(showNumber));
                        updateShowModel.setPhoneNumber(phoneNumber);
                        updateShowModel.setAvailability("Booked");
                        updateShowModel.setBookingTime(Timestamp.valueOf(LocalDateTime.now()));
                        showRepository.updateBooking(updateShowModel.getPhoneNumber(),updateShowModel.getBookingTime(),
                                updateShowModel.getAvailability(), updateShowModel.getSeatNumber(),updateShowModel.getShowNumber().toString());
                        log.info("Booked tickets for seat number: {}, Unique Ticket Id: {}",seatNumber,showSetupModel.getBookingId());
                    }
                }
            }
            return true;
        }
        log.info("Unable to find Show for showNumber: {}",showNumber);
        return false;
    }
}