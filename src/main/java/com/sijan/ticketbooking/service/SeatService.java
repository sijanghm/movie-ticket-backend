package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.entity.Seat;
import com.sijan.ticketbooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat addSeats(Seat seat) {
        return seatRepository.save(seat);
    }
}
