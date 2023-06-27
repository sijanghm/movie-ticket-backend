package com.sijan.ticketbooking.controller;


import com.sijan.ticketbooking.dto.request.BookingRequestDto;
import com.sijan.ticketbooking.entity.Booking;
import com.sijan.ticketbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     *things neesd to do
     *
     * need to add show time id and movie id while booking tickets
     * validate date and show time
     */
    @PostMapping("/book-ticket")
    public Booking bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
        return bookingService.bookTickets(bookingRequestDto);
    }
}
