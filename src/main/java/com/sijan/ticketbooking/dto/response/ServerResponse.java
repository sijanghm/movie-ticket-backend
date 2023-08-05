package com.sijan.ticketbooking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ServerResponse<T> implements Serializable {

    private boolean success;
    private String message;
    private T data;
}