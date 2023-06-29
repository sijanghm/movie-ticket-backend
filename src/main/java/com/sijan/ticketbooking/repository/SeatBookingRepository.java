package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {


    @Query("SELECT s FROM SeatBooking s WHERE s.bookedDate= :bookedDate and s.showTime.id = :showId")
    List<SeatBooking> findAllByBookedDateAndShowTime(@Param("bookedDate")LocalDate bookedDate, @Param("showId") Long showId);

    @Query("SELECT s FROM SeatBooking s WHERE s.seatId in :ids and s.bookedDate = :bookDate")
    List<SeatBooking> findAllBySeatIdsAndBookingDate(@Param("ids") List<String> ids, @Param("bookDate") LocalDate bookDate);
}
