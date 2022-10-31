package com.example.demo.Exception;

public class BookingExistsException extends Exception{

    public BookingExistsException(String message) {
        super("PhoneNumber already has a booking for this show. Only 1 booking per PhoneNumber is allowed!" +message);
    }
}
