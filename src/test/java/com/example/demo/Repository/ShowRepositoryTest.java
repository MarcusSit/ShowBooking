package com.example.demo.Repository;

import com.example.demo.Controller.ShowController;
import com.example.demo.Model.ShowSetupTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {
        "command.line.runner.enabled=false"})
class ShowRepositoryTest {
    @Autowired
    ShowRepository showRepository;

    private ShowSetupTest showSetupTest = new ShowSetupTest();

    @Test
    void shouldSaveAll() {
        var createList = showSetupTest.getShowSetupListTestDataForSetup();
        var actual = showRepository.saveAll(createList);

        assertThat(actual.size()).isEqualTo(10);
    }

}