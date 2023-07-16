package com.sijan.ticketbooking.dto;


import lombok.Data;

@Data
public class LoginDto {

    private String usernameOrEmail;
    private String password;
}
