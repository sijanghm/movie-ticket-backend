package com.sijan.ticketbooking.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MovieShow {

    private Long id;
    private String showTime;
    private String movieName;
    private String movieDescription;
    private LocalDate lastShowDate;
}
