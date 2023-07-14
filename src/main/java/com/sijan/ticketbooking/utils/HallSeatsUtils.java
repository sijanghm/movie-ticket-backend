package com.sijan.ticketbooking.utils;

import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.dto.HallSeatRow;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HallSeatsUtils {

    private HallSeatsUtils() {

    }

    public static Set<HallSeatRow> getAllHallSeats() {
        List<String> row = List.of("A", "B", "C", "D", "E");
        List<String> column = List.of("1", "2", "3", "4", "5", "6", "7", "8");

        return row.stream().map(r -> {
            Set<HallSeat> rowSeats =
                    column.stream()
                            .map(c ->
                                    HallSeat.builder()
                                            .seatId(r + c)
                                            .booked("UNBOOKED")
                                            .build()
                            ).collect(Collectors.toCollection(LinkedHashSet::new));
            return HallSeatRow
                    .builder()
                    .row(r)
                    .seats(rowSeats)
                    .build();
        }).collect(Collectors.toCollection(LinkedHashSet::new));

    }
}

