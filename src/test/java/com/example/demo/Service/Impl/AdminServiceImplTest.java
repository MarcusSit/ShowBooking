package com.example.demo.Service.Impl;

import com.example.demo.Controller.ShowController;
import com.example.demo.Exception.ShowDoesNotExistException;
import com.example.demo.Model.ShowSetupTest;
import com.example.demo.Repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false"})
class AdminServiceImplTest {
    private ShowSetupTest showSetupTest = new ShowSetupTest();
    @Mock
    private ShowRepository showRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void setup() {
    }

    @Test
    void shouldView() throws ShowDoesNotExistException {
        Mockito.when(showRepository.findByShowNumber(any())).thenReturn(Optional.of(showSetupTest.getShowSetupListTestDataForSetup()));
        var actual = adminService.view(1L);

        assertThat(actual.size()).isEqualTo(9);
    }
}