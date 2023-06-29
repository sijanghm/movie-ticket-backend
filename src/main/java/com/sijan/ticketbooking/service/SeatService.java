package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.HallSeat;
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

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;


    public List<HallSeat> getAllSeats(SeatRequestForBooking requestForBooking) {
        Optional<ShowTime> showTimeOptional = showTimeRepository.findById(requestForBooking.getShowId());

        if ( showTimeOptional.isPresent()){
          List<SeatBooking> seatBookings = seatBookingRepository.findAllByBookedDateAndShowTime(requestForBooking.getShowDate(), requestForBooking.getShowId());
          List<String> bookedSeats = seatBookings.stream().map(SeatBooking::getSeatId).toList();
          List<HallSeat> allHallSeats = HallSeatsUtils.getAllHallSeats();
          return  allHallSeats.stream()
                  .map(hallSeat -> {
                     if( bookedSeats.contains( hallSeat.getSeatId())) {
                         hallSeat.setBooked(true);
                     }
                     return hallSeat;
                  })
                  .toList();
        }
        throw new RuntimeException("Invalid Request: Invalid Show Id");
    }

}
