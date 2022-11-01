package com.example.demo.Repository;

import com.example.demo.Model.ShowSetup;
import com.example.demo.Model.ShowSetupTestData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Import(DataJpaTestConfiguration.class)
@DataJpaTest
class ShowRepositoryTest {
    @Autowired
    ShowRepository showRepository;

    private ShowSetupTestData showSetupTestData = new ShowSetupTestData();

    @Test
    void shouldSaveAll() {
        var createList = showSetupTestData.getShowSetupListTestDataForSetup();
        var actual = showRepository.saveAll(createList);
        assertThat(actual.size()).isEqualTo(9);
    }

    @Test
    void shouldFindByShowNumber() {
        var shouldSave = showSetupTestData.getShowSetupListTestDataForSetup();
        var saved = showRepository.saveAll(shouldSave);
        var actual = showRepository.findByShowNumber(1L);

        assertThat(actual.get()).isEqualTo(saved);
    }

    @Test
    void shouldFindByIdAndShowNumber() {
        List<ShowSetup> showSetups = new ArrayList<>();
        var shouldSave = showSetupTestData.getShowSetupTestDataWithBookedTickets(1L,"99993333");
        showSetups.add(shouldSave);
        var saved = showRepository.saveAll(showSetups);
        var actual = showRepository.findByIdAndPhoneNumber(1L,"99993333");

        assertThat(actual.get()).isEqualTo(saved.get(0));
    }
}