package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.HallSeatRow;
import com.sijan.ticketbooking.dto.request.SeatRequestForBooking;
import com.sijan.ticketbooking.entity.SeatBooking;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.SeatBookingRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import com.sijan.ticketbooking.utils.HallSeatsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;


    public Set<HallSeatRow> getAllSeats(SeatRequestForBooking requestForBooking) {
        Optional<ShowTime> showTimeOptional = showTimeRepository.findById(requestForBooking.getShowId());

        if ( showTimeOptional.isPresent()){
          List<SeatBooking> seatBookings = seatBookingRepository.findAllByBookedDateAndShowTime(requestForBooking.getShowDate(), requestForBooking.getShowId());
          List<String> bookedSeats = seatBookings.stream().map(SeatBooking::getSeatId).toList();
          Set<HallSeatRow> hallSeatRows = HallSeatsUtils.getAllHallSeats();
          hallSeatRows
                  .forEach(
                    hallSeatRow -> {
                      hallSeatRow.getSeats()
                              .forEach(
                              seat -> {
                                  if (bookedSeats.contains(seat.getSeatId())) {
                                      seat.setBooked("BOOKED");
                                  }
                              });
                  });
          return hallSeatRows;

        }
        throw new RuntimeException("Invalid Request: Invalid Show Id");
    }

}
