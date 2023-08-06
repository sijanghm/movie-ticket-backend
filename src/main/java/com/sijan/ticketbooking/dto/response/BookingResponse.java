package com.sijan.ticketbooking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingResponse {
    private String message;
    private Long showId;
    private List<String> seats;
    private LocalDate showDate;
    private Instant bookTimestamp;
    private Double ticketPrice;
    private Double totalPrice;
    private String paymentMethod;
    private Long paymentId;
}
