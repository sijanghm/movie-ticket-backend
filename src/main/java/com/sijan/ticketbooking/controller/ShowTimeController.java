package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.dto.request.ShowTimeDTO;
import com.sijan.ticketbooking.dto.response.RunningShow;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("show-time")
public class ShowTimeController {

   private ShowTimeService showTimeService;

   @Autowired
    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }

    @PostMapping()
    public ResponseEntity<ShowTime> addShowTimeForMovie(@RequestBody ShowTimeDTO showTimeDTO) {
        return ResponseEntity.ok(showTimeService.addShowTime(showTimeDTO));
    }

    @PutMapping()
    public ResponseEntity<ShowTime> updateShowTimeForMovie(@RequestBody ShowTimeDTO showTimeDTO){
       return ResponseEntity.ok(showTimeService.updateShowTime(showTimeDTO));
    }

    @GetMapping("/running")
    public ResponseEntity<List<RunningShow>> getAllShowTimes(){
       return ResponseEntity.ok(showTimeService.getAllRunningShows());
    }
}
