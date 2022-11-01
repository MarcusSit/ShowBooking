package com.example.demo.Utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class BookingUtils {
    public boolean checkIfEmpty(String input) {
        return !input.isBlank();
    }

    public boolean checkForMaxLength(int numberOfRows, int numberOfSeatsPerRow) {
        if (numberOfRows <= 26 && numberOfSeatsPerRow <= 10) {
            return true;
        }
        if (numberOfRows > 26){
            log.info("Max number of rows are 26!");
            return false;
        }
        if (numberOfSeatsPerRow > 10){
            log.info("Max number of seats per row is 10!");
            return false;
        }
        return false;
    }

    public void show2DArrayInConsole(int numberOfSeatsPerRow, int numberOfRows, List<Integer> bookedRow, List<Integer> bookedColumn) {
        System.out.print("  ");
        for (int i = 1; i <= numberOfSeatsPerRow; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= numberOfRows; i++) {
            String alphabetFromInt = String.valueOf((char) (i + 'A' - 1));
            System.out.print(alphabetFromInt + " ");
            for (int j = 0; j < numberOfSeatsPerRow; j++) {
                if (bookedRow.size() > 0 && bookedColumn.size() > 0) {
                    for (int bk = 0; bk < bookedRow.size(); bk++) {
                        for (int bc = 0; bc < bookedColumn.size(); bc++) {
                            if ((bookedRow.get(bk) == i) && (bookedColumn.get(bc) == j + 1)) {
                                System.out.print('X' + " ");
                            } else
                                System.out.print('A' + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    public List<String> separateByComma(String input) {
        if (input.contains(",")) {
            return Arrays.asList(input.split(","));
        }
        return List.of(input);
    }

    public long calculateDifferenceInTime(Timestamp bookingTime, Timestamp cancellationTime) {
        LocalDateTime bookingTimeInLocalDateTime = bookingTime.toLocalDateTime();
        LocalDateTime cancellationTimeInLocalDateTime = cancellationTime.toLocalDateTime();
        return Duration.between(bookingTimeInLocalDateTime, cancellationTimeInLocalDateTime).toMinutes();
    }
}
