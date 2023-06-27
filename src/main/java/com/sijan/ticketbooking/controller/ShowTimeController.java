package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.dto.request.ShowTimeDTO;
import com.sijan.ticketbooking.entity.ShowTime;
import com.sijan.ticketbooking.repository.ShowTimeRepository;
import com.sijan.ticketbooking.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowTimeController {

   private ShowTimeService showTimeService;

   @Autowired
    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }


    @PostMapping("/show-time")
    public ShowTime addShowTimeForMovie(@RequestBody ShowTimeDTO showTimeDTO) {
        return showTimeService.addShowTime(showTimeDTO);
    }
}
