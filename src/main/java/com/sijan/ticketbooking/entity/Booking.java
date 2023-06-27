package com.sijan.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private Instant bookedAt = Instant.now();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_booked", referencedColumnName = "id")
    private Seat seat;

}
