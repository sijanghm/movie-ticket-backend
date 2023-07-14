package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.dto.request.RunningShowRequestDTO;
import com.sijan.ticketbooking.dto.request.ShowTimeDTO;
import com.sijan.ticketbooking.dto.response.MovieShow;
import com.sijan.ticketbooking.dto.response.RunningShow;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("show-time")
public class ShowTimeController {

   private final ShowTimeService showTimeService;

   @Autowired
    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }

    @PostMapping()
    public ResponseEntity<MovieShow> addShowTimeForMovie(@RequestBody ShowTimeDTO showTimeDTO) {
       ShowTime showTime = showTimeService.addShowTime(showTimeDTO);
       MovieShow movieShow = MovieShow.builder()
               .id(showTime.getId())
               .movieName(showTime.getMovie().getMovieName())
               .movieDescription(showTime.getMovie().getDescription())
               .showTime(showTime.getShowTime())
               .lastShowDate(showTime.getLastShowDate())
               .build();

        return ResponseEntity.ok(movieShow);
    }

    @PutMapping()
    public ResponseEntity<MovieShow> updateShowTimeForMovie(@RequestBody ShowTimeDTO showTimeDTO){
        ShowTime showTime = showTimeService.updateShowTime(showTimeDTO);
        MovieShow movieShow = MovieShow.builder()
                .id(showTime.getId())
                .movieName(showTime.getMovie().getMovieName())
                .movieDescription(showTime.getMovie().getDescription())
                .showTime(showTime.getShowTime())
                .lastShowDate(showTime.getLastShowDate())
                .build();
       return ResponseEntity.ok(movieShow);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieShow>> getAllShows(){
       List<ShowTime> showTimeList = showTimeService.getAllShows();
       List<MovieShow> movieShowList = showTimeList
               .stream()
               .map(showTime -> MovieShow.builder()
                       .id(showTime.getId())
                       .movieName(showTime.getMovie().getMovieName())
                       .movieDescription(showTime.getMovie().getDescription())
                       .showTime(showTime.getShowTime())
                       .lastShowDate(showTime.getLastShowDate())
                       .build()
               ).toList();
       return ResponseEntity.ok(movieShowList);
    }

    @PostMapping("/running")
    public ResponseEntity<List<RunningShow>> getAllShowTimes(@RequestBody RunningShowRequestDTO showRequestDTO){
       return ResponseEntity.ok(showTimeService.getAllRunningShows(showRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunningShow> getRunningShowById(@PathVariable("id") Long showId) {
       return ResponseEntity.ok(showTimeService.getShowById(showId));
    }
}
