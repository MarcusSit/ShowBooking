package com.example.demo.Repository;

import com.example.demo.Model.ShowSetupModel;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;


class ShowRepositoryTest {


    private ShowSetupModel showSetupModel = new ShowSetupModel();

    private List<ShowSetupModel> showSetupModelList = new ArrayList<>();

    @Test
    void shouldSaveAll() {
        showSetupModel.setShowNumber(2L);
        showSetupModel.setAvailability("Available");
        showSetupModel.setCancellationWindow(3L);
        showSetupModel.setBookingId(null);
        showSetupModel.setSeatNumber("H3");

        showSetupModelList.add(showSetupModel);
    }

}