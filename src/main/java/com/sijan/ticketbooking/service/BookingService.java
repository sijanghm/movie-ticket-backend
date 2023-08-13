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
import com.sijan.ticketbooking.utils.CustomMultipartFile;
import com.sijan.ticketbooking.utils.EmailSendUtils;
import com.sijan.ticketbooking.utils.HallSeatsUtils;
import com.sijan.ticketbooking.utils.TicketPdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final SeatBookingRepository seatBookingRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserService userService;
    private final PaymentRepository paymentRepository;
    private final EmailSendUtils emailSendUtils;
    private final TicketPdfGenerator pdfGenerator;

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
               //validate seats in hall
               validateSeatId(bookingRequestDto, hallSeatList);
               //validate seats booking cases
               validateForSeatBooked(bookingRequestDto);

               //maintain payment record
               final Payment paymentRecord = recordPayment(bookingRequestDto, optionalShowTime, optionalUser);

               //start seat booking record
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

               Map<String, String> ticketDetails = new LinkedHashMap<>();
               ticketDetails.put("Seats", bookingRequestDto.getSeatIds().stream().collect(Collectors.joining(", ")));
               ticketDetails.put("Ticket Count", String.valueOf(bookingRequestDto.getSeatIds().size()));
               ticketDetails.put("Ticket Price", "NPR. " + optionalShowTime.get().getTicketPrice().toString());
               ticketDetails.put("Total", "NPR. " + paymentRecord.getTotalPrice().toString());
               ticketDetails.put("Payment Id", paymentRecord.getId().toString());
               ticketDetails.put("Payment Method", paymentRecord.getPaymentMethod());
               ticketDetails.put("Show Date", bookingRequestDto.getShowDate().toString());

               //Generate Ticket Info PDF
               CustomMultipartFile emailPdf = new CustomMultipartFile(pdfGenerator.createPdf(ticketDetails),"ticket-confirmation-detail.pdf", MediaType.APPLICATION_PDF.toString());

               List<MultipartFile> attachments =  new ArrayList<>();
               attachments.add(emailPdf);

               String emailBody = " Dear " + optionalUser.get().getName() + ", <br>"
                       + "Your ticket(s) has been booked and confirmed. <br>"
                       + "Please find ticket details in attachments."
                       + "<br> <br>"
                       + "Warm Regards,<br>"
                       + "Movie Hall";

               //inform user with email
               emailSendUtils.sendMail(attachments,  optionalUser.get().getEmail(),  "Ticket Booked",  emailBody );

               //send response to user
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
        payment.setMovie(optionalShowTime.get().getMovie());

        return paymentRepository.save(payment);
    }

    private static void validateShow(Optional<ShowTime> optionalShowTime) {

    }

    private void validateForSeatBooked(BookingRequestDto bookingRequestDto) {
        //check if the seats are already booked
        List<SeatBooking> bookedSeats = seatBookingRepository.findAllBySeatIdsAndShowDate(bookingRequestDto.getSeatIds(), bookingRequestDto.getShowDate(), bookingRequestDto.getShowId());
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
