package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    @Query(value = "SELECT s from ShowTime s where s.movie.isShowing = true and s.lastShowDate >= :showDate")
    List<ShowTime> findAllRunningShow(@Param("showDate") LocalDate showDate );

    @Query(value = "SELECT s FROM ShowTime s WHERE s.movie.isShowing = true AND s.id = :id ")
    Optional<ShowTime> findShowTimeById(@Param("id") Long id);
}
