package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "ShowSetup")
public class ShowSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column
    private Long showNumber;
    @Column
    private Long numberOfRows;
    @Column
    private Long numberOfSeatsPerRow;
    @Column
    private String seatNumber;
    @Column
    private Long cancellationWindow;
    @Column
    private String availability;
    @Column
    private String phoneNumber;
    @Column
    private Timestamp bookingTime;
}
