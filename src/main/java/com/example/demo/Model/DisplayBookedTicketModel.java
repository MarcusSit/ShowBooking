package com.example.demo.Model;

import lombok.Data;

@Data
public class DisplayBookedTicketModel {
    private long bookingId;
    private String seatNumber;
    private long showNumber;
}
