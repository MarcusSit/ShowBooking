package com.example.demo.Service;

import com.example.demo.Exception.ShowDoesNotExistException;
import com.example.demo.Model.ShowSetup;

import java.util.List;

public interface AdminService {
    void setup(long showNumber,int numberOfRows,int numberOfSeatsPerRow,long cancellationWindow);
    List<ShowSetup> view(long showNumber) throws ShowDoesNotExistException;

}
