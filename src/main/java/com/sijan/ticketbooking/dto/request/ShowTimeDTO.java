package com.sijan.ticketbooking.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowTimeDTO {
    private Long id;

    private Long movieId;

    private String showTime;

    private boolean isShowing;
}
