package com.sijan.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "SEAT_BOOKING_RECORD")
public class SeatBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatId;
    @Column(name = "bookeding_date")
    private LocalDate bookedDate;

    @Column(name = "booked_at")
    private Instant bookedTimeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private ShowTime showTime;
}
