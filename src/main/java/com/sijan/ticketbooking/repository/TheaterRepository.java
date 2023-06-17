package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
