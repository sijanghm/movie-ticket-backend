package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
