package com.sijan.ticketbooking.service;


import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.dto.request.BookingRequestDto;
import com.sijan.ticketbooking.dto.response.BookingResponse;
import com.sijan.ticketbooking.entity.SeatBooking;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.BookingRepo;
import com.sijan.ticketbooking.repository.SeatBookingRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import com.sijan.ticketbooking.utils.HallSeatsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepo bookingRepo;
    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;

    public BookingResponse bookTickets(BookingRequestDto bookingRequestDto) {

        Optional<ShowTime> optionalShowTime = showTimeRepository.findById(bookingRequestDto.getShowId());
        if(optionalShowTime.isEmpty()){
            //Bad Request
            throw new RuntimeException("Invalid Show Id");
        }

        List<String> hallSeatList = new ArrayList<>();
        HallSeatsUtils.getAllHallSeats()
                .forEach(seatrow -> {
           hallSeatList.addAll(seatrow.getSeats().stream().map(HallSeat::getSeatId).toList());
        });
        //Validate seats from users, if it is invalid
        bookingRequestDto.getSeatIds().forEach( seatId -> {
            if(!hallSeatList.contains(seatId)){
                //Bad Request
                throw new RuntimeException("Invalid Seat Ids");
            }
        });

        //check if the seats are already booked
        List<SeatBooking> bookedSeats = seatBookingRepository.findAllBySeatIdsAndBookingDate(bookingRequestDto.getSeatIds(), bookingRequestDto.getBookingDate());
        if(!bookedSeats.isEmpty() ){
            //return message best for response
            //Bad Request
            throw new RuntimeException("Some seats are already booked");
        }

        //save bookings
        bookingRequestDto.getSeatIds().forEach(seatId-> {
            SeatBooking seatBooking = new SeatBooking();
            seatBooking.setSeatId(seatId);
            seatBooking.setBookedFor(bookingRequestDto.getUserName());
            seatBooking.setBookedDate(bookingRequestDto.getBookingDate());
            seatBooking.setShowTime(optionalShowTime.get());
           seatBookingRepository.save(seatBooking);
        });

        return BookingResponse.builder()
                .username(bookingRequestDto.getUserName())
                .bookTimestamp(Instant.now())
                .bookDate(bookingRequestDto.getBookingDate())
                .seats(bookingRequestDto.getSeatIds())
                .build();
    }
}
