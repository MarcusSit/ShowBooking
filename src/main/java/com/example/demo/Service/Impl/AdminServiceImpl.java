package com.example.demo.Service.Impl;

import com.example.demo.Booking;
import com.example.demo.Exception.ShowDoesNotExistException;
import com.example.demo.Model.ShowSetup;
import com.example.demo.Repository.ShowRepository;
import com.example.demo.Service.AdminService;
import com.example.demo.Utils.BookingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final BookingUtils bookingUtils = new BookingUtils();

    private ShowRepository showRepository;

    public AdminServiceImpl(@Autowired ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public void setup(long showNumber, int numberOfRows, int numberOfSeatsPerRow, long cancellationWindow) {
        List<ShowSetup> showSetupList = new ArrayList<>();
        System.out.println("Show created with following seats:");
        List<Integer> bookedRows = new ArrayList<>();
        List<Integer> bookedColumns = new ArrayList<>();
        bookingUtils.show2DArrayInConsole(numberOfSeatsPerRow,numberOfRows,bookedRows,bookedColumns);
        for (int i = 1; i <= numberOfRows; i++) {
            String alphabetFromInt = String.valueOf((char) (i + 'A' - 1));
            for (int j = 1; j <= numberOfSeatsPerRow; j++) {
                String seatNumber = (alphabetFromInt+j).trim();
                setupNewShow(showNumber,seatNumber,cancellationWindow,numberOfRows,numberOfSeatsPerRow,showSetupList);
            }
        }
        showRepository.saveAll(showSetupList);
    }
    private void setupNewShow(long showNumber,String seatNumber,long cancellationWindow,long numberOfRows, long numberOfSeatsPerRow,List<ShowSetup> showSetupList) {
        ShowSetup showSetup = new ShowSetup();
        showSetup.setShowNumber(showNumber);
        showSetup.setSeatNumber(seatNumber);
        showSetup.setCancellationWindow(cancellationWindow);
        showSetup.setNumberOfRows(numberOfRows);
        showSetup.setNumberOfSeatsPerRow(numberOfSeatsPerRow);
        showSetup.setAvailability("Available");
        System.out.println(showSetup);
        showSetupList.add(showSetup);
    }

    @Override
    public List<ShowSetup> view(long showNumber) throws ShowDoesNotExistException {
       var showSetup = showRepository.findByShowNumber(showNumber);
        if (showSetup.isEmpty()) {
            throw new ShowDoesNotExistException(String.valueOf(showNumber));
        }
        return showSetup.get();
    }
}
