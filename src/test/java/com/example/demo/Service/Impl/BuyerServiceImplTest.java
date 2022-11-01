package com.example.demo.Service.Impl;

import com.example.demo.Exception.*;
import com.example.demo.Model.DisplayBookedTicketModelTestData;
import com.example.demo.Model.ShowSetup;
import com.example.demo.Model.ShowSetupTestData;
import com.example.demo.Repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
class BuyerServiceImplTest {

    private DisplayBookedTicketModelTestData displayBookedTicketModelTestData = new DisplayBookedTicketModelTestData();
    private ShowSetupTestData showSetupTestData = new ShowSetupTestData();

    @Mock
    private ShowRepository showRepository;
    @InjectMocks
    private BuyerServiceImpl buyerService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void availabilityOfShows() throws ShowDoesNotExistException {
        Mockito.when(showRepository.findByShowNumber(any())).thenReturn(Optional.ofNullable(showSetupTestData.getShowSetupListTestDataForSetup()));
        var actual = buyerService.availabilityOfShows(1L);
        List<String> availableShows = new ArrayList<>();
        for (ShowSetup showSetup : showSetupTestData.getShowSetupListTestDataForSetup()){
            availableShows.add(showSetup.getSeatNumber());
        }
        assertThat(actual.size()).isEqualTo(availableShows.size());
    }

    @Test
    void bookShows() throws BookingExistsException, SeatAlreadyBookedException, ShowDoesNotExistException {
        Mockito.when(showRepository.findByShowNumber(any())).thenReturn(Optional.ofNullable(showSetupTestData.getShowSetupListTestDataWithBookingId()));
        var actual =buyerService.bookShows(1L,"99993333","A1,,A2,A3");
        var expected = displayBookedTicketModelTestData.getDisplayBookedTicketModelList();
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual.get(0)).isEqualTo(expected.get(0));
        assertThat(actual.get(1)).isEqualTo(expected.get(1));
        assertThat(actual.get(2)).isEqualTo(expected.get(2));

    }

    @Test
    void cancelTicket() throws TicketNotFoundException, CancellationWindowExpiredException {
        Mockito.when(showRepository.findByIdAndPhoneNumber(any(),any())).thenReturn(Optional.ofNullable(showSetupTestData.getShowSetupTestDataWithBookedTickets(1L, "99993333")));
        var actual = buyerService.cancelTicket(1L,"99993333");
        assertThat(actual).isEqualTo("1");
    }
}