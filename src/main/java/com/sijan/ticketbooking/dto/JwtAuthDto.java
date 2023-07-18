package com.sijan.ticketbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthDto {

    private String accessToken;
    private String tokenType = "Bearer";
}
