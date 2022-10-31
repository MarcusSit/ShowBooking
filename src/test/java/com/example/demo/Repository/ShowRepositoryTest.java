package com.example.demo.Repository;

import com.example.demo.Model.ShowSetup;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;


class ShowRepositoryTest {


    private ShowSetup showSetup = new ShowSetup();

    private List<ShowSetup> showSetupList = new ArrayList<>();

    @Test
    void shouldSaveAll() {
        showSetup.setShowNumber(2L);
        showSetup.setAvailability("Available");
        showSetup.setCancellationWindow(3L);
        showSetup.setBookingId(null);
        showSetup.setSeatNumber("H3");

        showSetupList.add(showSetup);
    }

}