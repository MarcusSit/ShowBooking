package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class DisplayBookedTicketModelTestData {
    public DisplayBookedTicketModel getDisplayBookedTicketModel(long id,String seatNumber){
        DisplayBookedTicketModel displayBookedTicketModel = new DisplayBookedTicketModel();
        displayBookedTicketModel.setBookingId(id);
        displayBookedTicketModel.setSeatNumber(seatNumber);
        displayBookedTicketModel.setShowNumber(1L);
        return displayBookedTicketModel;
    }
    public List<DisplayBookedTicketModel> getDisplayBookedTicketModelList() {
        List<DisplayBookedTicketModel> displayBookedTicketModels = new ArrayList<>();
        var model1 = getDisplayBookedTicketModel(1L,"A1");
        var model2= getDisplayBookedTicketModel(2L,"A2");
        var model3= getDisplayBookedTicketModel(3L,"A3");
        displayBookedTicketModels.add(model1);
        displayBookedTicketModels.add(model2);
        displayBookedTicketModels.add(model3);
        return displayBookedTicketModels;
    }
}
