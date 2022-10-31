package com.example.demo.Exception;

public class TicketNotFoundException extends Exception{

    public TicketNotFoundException(String message) {
        super("Time window for Cancellation of ticket has expired. Sorry!" +message);
    }
}
