package com.sijan.ticketbooking.exceptions.handler;

import com.sijan.ticketbooking.dto.response.ServerResponse;
import com.sijan.ticketbooking.exceptions.BadRequestException;
import com.sijan.ticketbooking.exceptions.TicketBookedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadRequestException(BadCredentialsException exception, HttpServletRequest request){
        String message = !exception.getMessage().trim().isEmpty() ? exception.getMessage() :  "";
        return new ResponseEntity<>(buildErrorResponse(message), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception, HttpServletRequest request){
        String message = !exception.getMessage().trim().isEmpty() ? exception.getMessage() :  "Invalid Request";
        return new ResponseEntity<>(buildErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketBookedException.class)
    public ResponseEntity<?> handleBadRequestException(TicketBookedException exception, HttpServletRequest request){
        String message = !exception.getMessage().trim().isEmpty() ? exception.getMessage() :  "Invalid Request";
        return new ResponseEntity<>(buildErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    private ServerResponse<?> buildErrorResponse(String message) {
        return ServerResponse
                .builder()
                .success(false)
                .message(message)
                .build();
    }

    private ServerResponse<?> buildErrorResponse(String message, Object object) {
        return ServerResponse
                .builder()
                .success(false)
                .data(object)
                .message(message)
                .build();
    }
}
