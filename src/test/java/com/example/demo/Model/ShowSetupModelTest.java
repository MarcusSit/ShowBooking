package com.example.demo.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ShowSetupModelTest {
    ShowSetup showSetup = new ShowSetup();

    public ShowSetup getShowSetupModelTestData(long bookingId) {
        showSetup.setShowNumber(1L);
        showSetup.setSeatNumber("A1");
        showSetup.setAvailability("Available");
        showSetup.setBookingId(1L);
        showSetup.setNumberOfRows(3L);
        showSetup.setCancellationWindow(3L);
        showSetup.setNumberOfSeatsPerRow(3L);
        showSetup.setBookingId(bookingId);
        showSetup.setBookingTime(Timestamp.valueOf(LocalDateTime.now()));
        return showSetup;
    }
}