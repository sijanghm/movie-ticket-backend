package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    @Query(value = "SELECT s from ShowTime s where s.isShowing = true")
    List<ShowTime> findAllRunningShow();

}
