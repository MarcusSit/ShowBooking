package com.example.demo.Service;

import com.example.demo.Exception.*;
import com.example.demo.Model.DisplayBookedTicketModel;
import com.example.demo.Model.ShowSetup;

import java.util.List;

public interface BuyerService {

    List<String> availabilityOfShows(long showNumber) throws ShowDoesNotExistException;

    List<DisplayBookedTicketModel> bookShows(long showNumber, String phoneNumber, String seatNumbers) throws ShowDoesNotExistException, BookingExistsException, SeatAlreadyBookedException;

    String cancelTicket(long ticketNumber, String phoneNumber) throws TicketNotFoundException, CancellationWindowExpiredException;
}
