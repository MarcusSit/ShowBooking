package com.example.demo.Exception;

public class CancellationWindowExpiredException extends Exception{

    public CancellationWindowExpiredException(String message) {
        super("PhoneNumber already has a booking for this show. Only 1 booking per PhoneNumber is allowed!" +message);
    }
}
