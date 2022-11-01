package com.example.demo.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowSetupTest {


    public ShowSetup getShowSetupTestDataForSetup(long bookingId) {
        ShowSetup showSetup = new ShowSetup();
        showSetup.setShowNumber(1L);
        showSetup.setSeatNumber("A1");
        showSetup.setAvailability("Available");
        showSetup.setBookingId(1L);
        showSetup.setNumberOfRows(3L);
        showSetup.setCancellationWindow(3L);
        showSetup.setNumberOfSeatsPerRow(3L);
        showSetup.setBookingId(bookingId);
        showSetup.setBookingTime(null);
        return showSetup;
    }

    public List<ShowSetup> getShowSetupListTestDataForSetup() {
        List<ShowSetup> showSetups = new ArrayList<>();
        long numberOfRows = 3L;
        long numberOfSeatsPerRow = 3L;
        for (int i = 1; i <= numberOfRows; i++){
            String alphabetFromInt = String.valueOf((char) (i + 'A' - 1));
            for (int j = 1; j <= numberOfSeatsPerRow; j++) {
                String seatNumber = (alphabetFromInt+j).trim();
                ShowSetup showSetup = new ShowSetup();
                showSetup.setShowNumber(1L);
                showSetup.setSeatNumber(seatNumber);
                showSetup.setAvailability("Available");
                showSetup.setNumberOfRows((long) i);
                showSetup.setCancellationWindow(3L);
                showSetup.setNumberOfSeatsPerRow((long)j);
                showSetup.setBookingId(null);
                showSetup.setBookingTime(null);
                showSetups.add(showSetup);
            }
        }
        return showSetups;
    }
}