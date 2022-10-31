package com.example.demo.Controller;

import com.example.demo.Model.ShowSetupModel;
import com.example.demo.Repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ShowBookingControllerTest {

    @InjectMocks
    ShowBookingController showBookingController;

    @Spy
    ShowBookingService showBookingService;

    @Spy
    ShowRepository showRepository;

    private Scanner mockScanner = mock(Scanner.class);

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(showBookingService,"showRepository",showRepository);
        ReflectionTestUtils.setField(showBookingController,"showBookingService",showBookingService);
    }

    @Test
    void shouldTestFlow() {
        assertFalse(showBookingController.flow());
    }

    @Test
    void shouldCheckForMaxLength() {

    }

    @Test
    void shouldCheckForInput() {
        when(showBookingController.getNextLine(mockScanner)).thenReturn("Admin");
        assertThat(showBookingController.getNextLine(mockScanner)).isEqualTo("Admin");
    }

    @Test
    void shouldCheckIfEmpty() {
        assertThat(showBookingController.checkIfEmpty("")).isEqualTo(false);
        assertThat(showBookingController.checkIfEmpty("TEST")).isEqualTo(true);
    }

    @Test
    void shouldCheckFlowForBuyer() {

        String mockStringInput = "book 1 333333 A1,A2";
        showBookingController.flowForBuyer(mockStringInput);
    }

    @Test
    void shouldCheckFlowForBuyerAvailability() {

        String mockStringInput = "Availability 1";
        showBookingController.flowForBuyer(mockStringInput);
    }

    @Test
    void shouldCheckFlowForAdminSetup() {

        String mockStringInput = "Setup 1 3 3 3";
        showBookingController.flowForAdmin(mockStringInput);
    }

    @Test
    void shouldCheckFlowForAdminView() {

        String mockStringInput = "Availability 1";
        showBookingController.flowForAdmin(mockStringInput);
    }
}