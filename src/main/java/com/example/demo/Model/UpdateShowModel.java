package com.example.demo.Model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateShowModel {
    private Long showNumber;
    private String phoneNumber;
    private String seatNumber;
    private String availability;
    private Timestamp bookingTime;
}
