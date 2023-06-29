package com.sijan.ticketbooking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RunningShowRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate showDate;
}
