package com.example.demo.Utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
class BookingUtilsTest {

    private BookingUtils bookingUtils = new BookingUtils();

    @Test
    void checkIfEmpty() {
        assertFalse(bookingUtils.checkIfEmpty(""));
        assertTrue(bookingUtils.checkIfEmpty("ASD"));
    }

    @Test
    void checkForMaxLength() {
        assertTrue(bookingUtils.checkForMaxLength(26,10));
        assertFalse(bookingUtils.checkForMaxLength(27,10));
        assertFalse(bookingUtils.checkForMaxLength(26,11));
    }

    @Test
    void separateByComma() {
        var actual = bookingUtils.separateByComma("A1,A2");
        List<String> expected = new ArrayList<>();
        expected.add("A1");
        expected.add("A2");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void separateByCommaFor1Input() {
        var actual = bookingUtils.separateByComma("A1");
        List<String> expected = new ArrayList<>();
        expected.add("A1");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void calculateDifferenceInTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1= LocalDateTime.parse("2014-11-25 19:00:00", formatter);
        LocalDateTime dateTime2= LocalDateTime.parse("2014-11-25 16:00:00", formatter);
        var actual = bookingUtils.calculateDifferenceInTime(Timestamp.valueOf(dateTime1),Timestamp.valueOf(dateTime2));
        assertThat(actual).isEqualTo(-180L);
    }
}