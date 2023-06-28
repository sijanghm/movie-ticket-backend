package com.sijan.ticketbooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HallSeat {

    private String seatId;
    private boolean booked;
}
