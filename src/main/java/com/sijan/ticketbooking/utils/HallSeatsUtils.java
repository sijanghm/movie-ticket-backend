package com.sijan.ticketbooking.utils;

import com.sijan.ticketbooking.dto.HallSeat;

import java.util.LinkedList;
import java.util.List;

public class HallSeatsUtils {

    private HallSeatsUtils() {

    }

    public static List<HallSeat> getAllHallSeats(){
        List<Integer> seatColumns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> seatRow = List.of("A", "B", "C", "D", "E", "F", "G", "H");

        List<HallSeat> allHallSeats = new LinkedList<>();
        seatRow.forEach(row -> seatColumns.forEach(column -> {
            HallSeat hallSeat = HallSeat.builder()
                    .seatId(row + column)
                    .booked(false)
                    .build();
            allHallSeats.add(hallSeat);
        }));
        return allHallSeats;
    }
}

