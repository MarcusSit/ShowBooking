package com.example.demo.Repository;

import com.example.demo.Model.ShowSetupTestData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
class ShowRepositoryTest {
    @Autowired
    ShowRepository showRepository;

    private ShowSetupTestData showSetupTestData = new ShowSetupTestData();

    @Test
    void shouldSaveAll() {
        var createList = showSetupTestData.getShowSetupListTestDataForSetup();
        var actual = showRepository.saveAll(createList);

        assertThat(actual.size()).isEqualTo(10);
    }

}