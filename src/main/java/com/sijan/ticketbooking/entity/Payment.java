package com.sijan.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "PAYMENT")
public class Payment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "METHOD")
    private String paymentMethod;
    @Column(name = "PRICE")
    private Double totalPrice;
    @Column(name = "PAID_BY")
    private String paidBy;
    @Column(name = "PAYMENT_TIMESTAMP")
    private Instant paymentTimestamp = Instant.now();

}
