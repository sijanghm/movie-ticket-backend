package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.request.ShowTimeDTO;
import com.sijan.ticketbooking.entity.Movie;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.MovieRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowTimeService {

    private ShowTimeRepository showTimeRepository;

    private MovieRepository movieRepository;

    @Autowired
    public ShowTimeService(ShowTimeRepository showTimeRepository, MovieRepository movieRepository) {
        this.showTimeRepository = showTimeRepository;
        this.movieRepository = movieRepository;
    }

    public ShowTime addShowTime(ShowTimeDTO showTimeDTO) {
        Optional<Movie> movieOptional = movieRepository.findById(showTimeDTO.getMovieId());
        if(movieOptional.isPresent()){
            ShowTime showTime = new ShowTime();
            showTime.setMovie(movieOptional.get());
            showTime.setShowTime(showTimeDTO.getShowTime());
            showTimeRepository.save(showTime);
            return showTime;
        }
        throw new RuntimeException("Invalid movie id");

    }
}
