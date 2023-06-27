package com.sijan.ticketbooking.service;


import com.sijan.ticketbooking.dto.request.BookingRequestDto;
import com.sijan.ticketbooking.entity.Booking;
import com.sijan.ticketbooking.entity.Seat;
import com.sijan.ticketbooking.repository.BookingRepo;
import com.sijan.ticketbooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    private BookingRepo bookingRepo;

    private SeatRepository seatRepository;

    @Autowired
    public BookingService(BookingRepo bookingRepo, SeatRepository seatRepository) {
        this.bookingRepo = bookingRepo;
        this.seatRepository = seatRepository;
    }

    public Booking bookTickets(BookingRequestDto bookingRequestDto) {
        Optional<Seat> seatOptional = seatRepository.findById(bookingRequestDto.getSeatId());
        if(seatOptional.isPresent()) {
            Booking booking = new Booking();
            booking.setSeat(seatOptional.get());
            booking.setUserName(bookingRequestDto.getUserName());
            return bookingRepo.save(booking);
        }
        throw  new RuntimeException("seat not found");
    }
}
