package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.dto.HallSeatRow;
import com.sijan.ticketbooking.dto.request.SeatRequestForBooking;
import com.sijan.ticketbooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("/seats")
@RestController
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping()
    public ResponseEntity<Set<HallSeatRow>> getAllSeats(@RequestBody SeatRequestForBooking requestForBooking){
        return ResponseEntity.ok(seatService.getAllSeats(requestForBooking));
    }
}
