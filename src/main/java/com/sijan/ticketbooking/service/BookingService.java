package com.sijan.ticketbooking.service;


import com.sijan.ticketbooking.dto.HallSeat;
import com.sijan.ticketbooking.dto.request.BookingRequestDto;
import com.sijan.ticketbooking.dto.response.BookingResponse;
import com.sijan.ticketbooking.entity.Payment;
import com.sijan.ticketbooking.entity.SeatBooking;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.entity.User;
import com.sijan.ticketbooking.exceptions.BadRequestException;
import com.sijan.ticketbooking.exceptions.TicketBookedException;
import com.sijan.ticketbooking.repository.PaymentRepository;
import com.sijan.ticketbooking.repository.SeatBookingRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import com.sijan.ticketbooking.utils.HallSeatsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    public BookingResponse bookTickets(BookingRequestDto bookingRequestDto) {

       Optional<User> optionalUser = userService.getCurrentUser();
       if(optionalUser.isPresent()) {
           Optional<ShowTime> optionalShowTime = showTimeRepository.findById(bookingRequestDto.getShowId());
           if(optionalShowTime.isPresent()){
               List<String> hallSeatList = new ArrayList<>();
               HallSeatsUtils
                       .getAllHallSeats()
                       .forEach(seatrow -> {
                           hallSeatList.addAll(seatrow.getSeats().stream().map(HallSeat::getSeatId).toList());
                       });

               validateSeatId(bookingRequestDto, hallSeatList);
               validateForSeatBooked(bookingRequestDto);

               final Payment paymentRecord = recordPayment(bookingRequestDto, optionalShowTime, optionalUser);

               List<SeatBooking> seatBookings = new ArrayList<>();
               //save bookings
               bookingRequestDto.getSeatIds().forEach(seatId-> {
                   SeatBooking seatBooking = new SeatBooking();
                   seatBooking.setSeatId(seatId);
                   seatBooking.setBookedBy(optionalUser.get().getName());
                   seatBooking.setShowDate(bookingRequestDto.getShowDate());
                   seatBooking.setShowTime(optionalShowTime.get());
                   seatBooking.setBookedTimeStamp(Instant.now());
                   seatBooking.setPayment(paymentRecord);
                   seatBookings.add(seatBookingRepository.save(seatBooking));
               });

               return BookingResponse
                       .builder()
                       .message("Ticket Booked successfully")
                       .showId(bookingRequestDto.getShowId())
                       .bookTimestamp(Instant.now())
                       .showDate(bookingRequestDto.getShowDate())
                       .seats(bookingRequestDto.getSeatIds())
                       .paymentMethod(paymentRecord.getPaymentMethod())
                       .ticketPrice(optionalShowTime.get().getTicketPrice())
                       .totalPrice(paymentRecord.getTotalPrice())
                       .paymentId(paymentRecord.getId())
                       .build();
           }
           throw new BadRequestException("Invalid Show Requested ");
       }
        throw new BadRequestException("User Session Exception");
    }

    private Payment recordPayment(BookingRequestDto bookingRequestDto, Optional<ShowTime> optionalShowTime, Optional<User> optionalUser) {
        Double totalPrice = bookingRequestDto.getSeatIds().size() * optionalShowTime.get().getTicketPrice();

        Payment payment = new Payment();
        payment.setPaymentMethod(bookingRequestDto.getPaymentMethod());
        payment.setPaymentTimestamp(Instant.now());
        payment.setPaidBy(optionalUser.get().getUsername());
        payment.setTotalPrice(totalPrice);

        return paymentRepository.save(payment);
    }

    private static void validateShow(Optional<ShowTime> optionalShowTime) {

    }

    private void validateForSeatBooked(BookingRequestDto bookingRequestDto) {
        //check if the seats are already booked
        List<SeatBooking> bookedSeats = seatBookingRepository.findAllBySeatIdsAndShowDate(bookingRequestDto.getSeatIds(), bookingRequestDto.getShowDate());
        if(!bookedSeats.isEmpty()) {
            throw new TicketBookedException("Some seats " + bookedSeats.stream().map(SeatBooking::getSeatId).collect(Collectors.joining(", "))  + " are already booked. Please try with other seats");
        }
    }

    private static void validateSeatId(BookingRequestDto bookingRequestDto, List<String> hallSeatList) {
        //validate for possible hall seats
        bookingRequestDto.getSeatIds().forEach(seatId -> {
            if(!hallSeatList.contains(seatId)){
                throw new BadRequestException("Invalid Seat Ids");
            }
        });
    }
}
