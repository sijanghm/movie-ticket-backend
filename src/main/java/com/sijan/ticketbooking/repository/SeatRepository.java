package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
