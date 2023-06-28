package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.entity.Seat;
import com.sijan.ticketbooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("seats")
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

    @GetMapping("/all")
    public ResponseEntity<List<HallSeat>> getAllSeats(){
        return ResponseEntity.ok(seatService.getAllSeats());
    }
}
