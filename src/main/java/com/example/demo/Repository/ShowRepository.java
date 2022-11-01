package com.example.demo.Repository;

import com.example.demo.Model.ShowSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<ShowSetup,Integer> {
    Optional<List<ShowSetup>> findByShowNumber(Long showNumber);

    @Query(value = "SELECT * FROM SHOW_SETUP WHERE BOOKING_ID = ?1 AND PHONE_NUMBER = ?2",nativeQuery = true)
    Optional<ShowSetup> findByIdAndPhoneNumber(Long bookingId, String phoneNumber);

    @Modifying
    @Query(value = "UPDATE SHOW_SETUP SET PHONE_NUMBER = ?1,BOOKING_TIME = ?2,AVAILABILITY =?3 WHERE SEAT_NUMBER =?4 AND SHOW_NUMBER =?5", nativeQuery = true)
    void updateBooking(String phoneNumber,Timestamp timestamp,String availability,String seatNumber, long showNumber);
}
