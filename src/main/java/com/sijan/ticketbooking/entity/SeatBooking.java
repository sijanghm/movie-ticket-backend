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
    @Column(name = "show_date")
    private LocalDate showDate;

    @Column(name = "booked_at")
    private Instant bookedTimeStamp = Instant.now();

    @Column(name = "booked_by")
    private String bookedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private ShowTime showTime;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
