package com.example.demo.Service.Impl;

import com.example.demo.Exception.*;
import com.example.demo.Model.DisplayBookedTicketModel;
import com.example.demo.Model.ShowSetup;
import com.example.demo.Repository.ShowRepository;
import com.example.demo.Service.BuyerService;
import com.example.demo.Utils.BookingUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    private BookingUtils bookingUtils = new BookingUtils();
    ShowRepository showRepository;

    public BuyerServiceImpl(@Autowired ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public List<String> availabilityOfShows(long showNumber) throws ShowDoesNotExistException {
        var availableSeatsForShow = showRepository.findByShowNumber(showNumber);
        if (availableSeatsForShow.isEmpty()) {
            throw new ShowDoesNotExistException(String.valueOf(showNumber));
        }
        List<String> availableSeatNumbers = new ArrayList<>();
        for (ShowSetup showSetup : availableSeatsForShow.get()) {
            String availability = showSetup.getAvailability();
            if (availability.equalsIgnoreCase("Available")) {
                availableSeatNumbers.add(showSetup.getSeatNumber());
            }
        }
        return availableSeatNumbers;
    }

    @Override
    public List<DisplayBookedTicketModel> bookShows(long showNumber, String phoneNumber, String seatNumbers) throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException {
        var listOfSeats = bookingUtils.separateByComma(seatNumbers);
        var availableShows = showRepository.findByShowNumber(showNumber);
        if (availableShows.isEmpty()) {
            throw new ShowDoesNotExistException(String.valueOf(showNumber));
        }
        List<DisplayBookedTicketModel> bookedSeats = new ArrayList<>();
        for (ShowSetup showSetup : availableShows.get()) {
            if (!StringUtils.isNullOrEmpty(showSetup.getPhoneNumber())) {
                if (showSetup.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                    throw new BookingExistsException(phoneNumber);
                }
            }
            for (String seatNumber: listOfSeats) {
                if (showSetup.getSeatNumber().equals(seatNumber)) {
                    if (showSetup.getAvailability().equalsIgnoreCase("Booked")) {
                        throw new SeatAlreadyBookedException(String.valueOf(showNumber));
                    }
                    showRepository.updateBooking(phoneNumber,Timestamp.valueOf(LocalDateTime.now()),
                            "Booked", seatNumber,String.valueOf(showNumber));
                    DisplayBookedTicketModel displayBookedTicketModel = new DisplayBookedTicketModel();
                    displayBookedTicketModel.setBookingId(showSetup.getBookingId());
                    displayBookedTicketModel.setShowNumber(showNumber);
                    displayBookedTicketModel.setSeatNumber(seatNumber);
                    bookedSeats.add(displayBookedTicketModel);
                    log.info("Booked tickets for seat number: {}, Unique Ticket Id: {} for Show Number: {}",seatNumber, showSetup.getBookingId(), showNumber);
                }
            }
        }
        return bookedSeats;
    }

    @Override
    public String cancelTicket(long ticketNumber, String phoneNumber) throws TicketNotFoundException, CancellationWindowExpiredException {
        var findByTicketNumber = showRepository.findByIdAndPhoneNumber(ticketNumber,phoneNumber);
        if (findByTicketNumber.isEmpty()){
            throw new TicketNotFoundException(String.valueOf(ticketNumber));
        }
        var foundToBeCancelledTicket = findByTicketNumber.get();
        if(bookingUtils.calculateDifferenceInTime(foundToBeCancelledTicket.getBookingTime(),
                Timestamp.valueOf(LocalDateTime.now())) > foundToBeCancelledTicket.getCancellationWindow()){
            throw new CancellationWindowExpiredException(String.valueOf(ticketNumber));
        }
        showRepository.updateBooking(phoneNumber,null,"Available",
                foundToBeCancelledTicket.getSeatNumber(),foundToBeCancelledTicket.getShowNumber().toString());
        return String.valueOf(ticketNumber);
    }
}
