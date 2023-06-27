package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.entity.Seat;
import com.sijan.ticketbooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {

    private SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/add-seat")
    public Seat addSeats(@RequestBody Seat seat) {
        return seatService.addSeats(seat);
    }
}
