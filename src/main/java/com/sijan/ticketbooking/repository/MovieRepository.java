package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select m from Movie m where m.isShowing = true")
    List<Movie> findAll();
}
