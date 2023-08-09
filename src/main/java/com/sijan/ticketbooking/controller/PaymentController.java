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

    @GetMapping("analytics/week/payment/method")
    public ResponseEntity<?> weekAnalyticsPayment() {
        return ResponseEntity.ok(paymentService.getWeekAnalyticsByPaymentMethod());
    }

    @GetMapping("analytics/week/movies")
    public ResponseEntity<?> weekAnalyticsMovie() {
        return ResponseEntity.ok(paymentService.getWeekAnalyticByMovies());
    }

    @GetMapping("analytics/week/daily")
    public ResponseEntity<?> weekAnalyticsDaily() {
        return ResponseEntity.ok(paymentService.getWeeklyAnalyticByDay());
    }

}
