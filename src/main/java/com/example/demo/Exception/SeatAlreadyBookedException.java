package com.example.demo.Exception;

public class SeatAlreadyBookedException extends Exception{

    public SeatAlreadyBookedException(String message) {
        super("Seat has already been booked for this Show: " +message);
    }
}
