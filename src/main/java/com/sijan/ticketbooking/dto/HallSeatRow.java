package com.sijan.ticketbooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class HallSeatRow {

    private String row;
    private Set<HallSeat> seats;

}
