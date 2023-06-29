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

    private List<String> seats;
    private String username;
    private LocalDate bookDate;
    private Instant bookTimestamp;
}
