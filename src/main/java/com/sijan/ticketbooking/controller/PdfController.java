package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.utils.TicketPdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/createPdf")
public class PdfController {

    @Autowired
    private TicketPdfGenerator ticketPdfGenerator;

    @GetMapping
    public ResponseEntity<InputStreamResource> createPdf() {


        Map<String, String> ticketDetails = new LinkedHashMap<>();
        ticketDetails.put("Seats", "A1");
        ticketDetails.put("Ticket Count", String.valueOf(1));
        ticketDetails.put("Ticket Price", "NPR  100");
        ticketDetails.put("Total", "100");
        ticketDetails.put("Payment Id", "PTM - 001");
        ticketDetails.put("Payment Method", "E-Sewa");
        ticketDetails.put("Show Date", "123456");

        byte[] pdf = ticketPdfGenerator.createPdf(ticketDetails);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline:file=lcwd.pdf");
        return  ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(new ByteArrayInputStream(pdf)));
    }
}
