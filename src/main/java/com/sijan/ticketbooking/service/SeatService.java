package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.dto.request.SeatRequestForBooking;
import com.sijan.ticketbooking.entity.SeatBooking;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.SeatBookingRepository;
import com.sijan.ticketbooking.repository.SeatRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;

    private final List<String> seatRow = List.of("A", "B", "C", "D", "E", "F", "G", "H");
    private final List<Integer> seatColumns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


    public List<HallSeat> getAllSeats(SeatRequestForBooking requestForBooking) {
        Optional<ShowTime> showTimeOptional = showTimeRepository.findById(requestForBooking.getShowId());

        if ( showTimeOptional.isPresent()){
          List<SeatBooking> seatBookings = seatBookingRepository.findAllByBookedDateAndShowTime(requestForBooking.getShowDate(), requestForBooking.getShowId());
          List<String> bookedSeats = seatBookings.stream().map(SeatBooking::getSeatId).toList();
          List<HallSeat> allHallSeats = getAllHallSeats();
          return  allHallSeats.stream()
                  .filter(hallSeat -> !bookedSeats.contains(hallSeat.getSeatId()))
                  .toList();
        }
        throw new RuntimeException("Invalid Request: Invalid Show Id");
    }

    public List<HallSeat>  getAllHallSeats(){
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
