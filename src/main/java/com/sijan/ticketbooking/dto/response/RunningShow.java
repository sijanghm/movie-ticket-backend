package com.sijan.ticketbooking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RunningShow {

    private Long id;
    private String showTime;
    private String movieName;
    private String movieDescription;
    private Double ticketPrice;
}
