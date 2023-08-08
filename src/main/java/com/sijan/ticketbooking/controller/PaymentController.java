package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.entity.Payment;
import com.sijan.ticketbooking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentList);
    }

    @GetMapping("analytics/week")
    public ResponseEntity<?> weekAnalytics() {
        return ResponseEntity.ok(paymentService.getWeekAnalytics());
    }

}
