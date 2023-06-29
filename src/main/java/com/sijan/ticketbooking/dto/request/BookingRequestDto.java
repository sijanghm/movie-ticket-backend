package com.sijan.ticketbooking.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingRequestDto {

    private String userName;
    private List<String> seatIds;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    private Long showId;
}
