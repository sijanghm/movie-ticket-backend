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


    @Query("SELECT s FROM SeatBooking s WHERE s.showDate= :showDate and s.showTime.id = :showId")
    List<SeatBooking> findAllByShowDateAndShowTime(@Param("showDate")LocalDate showDate, @Param("showId") Long showId);

    @Query("SELECT s FROM SeatBooking s WHERE s.seatId in :ids and s.showDate = :showDate")
    List<SeatBooking> findAllBySeatIdsAndShowDate(@Param("ids") List<String> ids, @Param("showDate") LocalDate showDate);
}
