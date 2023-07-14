package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.request.RunningShowRequestDTO;
import com.sijan.ticketbooking.dto.request.ShowTimeDTO;
import com.sijan.ticketbooking.dto.response.RunningShow;
import com.sijan.ticketbooking.entity.Movie;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.MovieRepository;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    private final MovieRepository movieRepository;

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
            showTime.setLastShowDate(showTimeDTO.getLastShowDate());
            showTimeRepository.save(showTime);
            return showTime;
        }
        throw new RuntimeException("Invalid movie id");

    }

    public ShowTime updateShowTime(ShowTimeDTO showTimeDTO) {
        Optional<ShowTime> showTimeOptional = showTimeRepository.findById(showTimeDTO.getId());
        if(showTimeOptional.isPresent()){
            ShowTime showTime = showTimeOptional.get();
            showTime.setId(showTimeOptional.get().getId());
            showTime.setMovie(showTimeOptional.get().getMovie());
            showTime.setShowTime(showTimeDTO.getShowTime());
            showTime.setLastShowDate(showTimeDTO.getLastShowDate());
            showTimeRepository.save(showTime);
        }
        throw new RuntimeException("Invalid Show Time Id");
    }

    public List<RunningShow> getAllRunningShows(RunningShowRequestDTO showRequestDTO){
        List<ShowTime> showTimeList = showTimeRepository.findAllRunningShow(showRequestDTO.getShowDate());
        return showTimeList
                .stream()
                .map(showTime ->
                        RunningShow.builder()
                                .id(showTime.getId())
                                .movieId(showTime.getMovie().getId())
                                .showTime(showTime.getShowTime())
                                .movieName(showTime.getMovie().getMovieName())
                                .movieDescription(showTime.getMovie().getDescription())
                                .build()
                ).toList();
    }

    public List<ShowTime> getAllShows() {
        return showTimeRepository.findAll();
    }

    public RunningShow getShowById(Long showId){
        Optional<ShowTime> optionalShowTime = showTimeRepository.findShowTimeById(showId);
        if(optionalShowTime.isPresent()){
            ShowTime showTime = optionalShowTime.get();
            return RunningShow.builder()
                    .id(showTime.getId())
                    .showTime(showTime.getShowTime())
                    .movieName(showTime.getMovie().getMovieName())
                    .movieDescription(showTime.getMovie().getDescription())
                    .build();
        }
        throw new RuntimeException("Invalid Request: invalid showTime id");
    }
}
