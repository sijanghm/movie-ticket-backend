package com.sijan.ticketbooking.controller;


import com.sijan.ticketbooking.dto.request.BookingRequestDto;
import com.sijan.ticketbooking.dto.response.BookingResponse;
import com.sijan.ticketbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book-ticket")
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.bookTickets(bookingRequestDto));
    }
}
