package com.sijan.ticketbooking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ShowTimeDTO {
    private Long id;

    private Long movieId;

    private String showTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastShowDate;

    private Double ticketPrice;

}
