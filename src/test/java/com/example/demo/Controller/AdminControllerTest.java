package com.example.demo.Controller;

import com.example.demo.Exception.*;
import com.example.demo.Service.Impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Scanner;
@RunWith(SpringJUnit4ClassRunner.class)
class AdminControllerTest {

    @Mock
    private AdminServiceImpl adminService;

    @Mock
    private Scanner scanner = new Scanner(System.in);

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void flowForAdmin() throws BookingExistsException, SeatAlreadyBookedException, ShowDoesNotExistException, TicketNotFoundException, CancellationWindowExpiredException {
        Mockito.when(scanner.nextLine()).thenReturn("setup 1 3 3 3");
        adminController.flowForAdmin();
    }
}