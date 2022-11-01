package com.example.demo.Service.Impl;

import com.example.demo.Exception.ShowDoesNotExistException;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
class AdminServiceImplTest {
    private ShowSetupTestData showSetupTestData = new ShowSetupTestData();
    @Mock
    private ShowRepository showRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void setup() {
        Mockito.when(showRepository.saveAll(any())).thenReturn(showSetupTestData.getShowSetupListTestDataForSetup());
        Mockito.when(showRepository.findByShowNumber(1L)).thenReturn(Optional.ofNullable(showSetupTestData.getShowSetupListTestDataForSetup()));
        adminService.setup(1L,3,3,3L);
        assertThat(showRepository.findByShowNumber(1L)).isNotEmpty();
    }

    @Test
    void shouldView9Seats() throws ShowDoesNotExistException {
        Mockito.when(showRepository.findByShowNumber(any())).thenReturn(Optional.of(showSetupTestData.getShowSetupListTestDataForSetup()));
        var actual = adminService.view(1L);
        assertThat(actual.size()).isEqualTo(9);
    }

}