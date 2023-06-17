package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.entity.Theater;
import com.sijan.ticketbooking.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {
    private TheaterRepository theaterRepository;
    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }
    public Theater addTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public List<Theater> getAllTheater() {
        return theaterRepository.findAll();
    }
}
