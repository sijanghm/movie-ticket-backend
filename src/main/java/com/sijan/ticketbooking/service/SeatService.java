package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.entity.Seat;
import com.sijan.ticketbooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    private final List<String> seatRow = List.of("A", "B", "C", "D", "E", "F", "G", "H");
    private final List<Integer> seatColumns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat addSeats(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<HallSeat> getAllSeats() {
        List<HallSeat> hallSeats = new LinkedList<>();
        seatRow.forEach(row -> seatColumns.forEach(column -> {
            HallSeat hallSeat = HallSeat.builder()
                    .seatId(row + column)
                    .booked(false)
                    .build();
            hallSeats.add(hallSeat);
        }));
        return hallSeats;
    }
}
