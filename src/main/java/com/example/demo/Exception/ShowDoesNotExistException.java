package com.example.demo.Exception;

public class ShowDoesNotExistException extends Exception{
    public ShowDoesNotExistException(String message) {
        super("Unable to find show for show number:" +message);
    }
}
