package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.entity.Payment;
import com.sijan.ticketbooking.entity.User;
import com.sijan.ticketbooking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Payment> getAllPayments() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if(optionalUser.isPresent()) {
            if (userService.isAdmin()) {
                return  paymentRepository.findAll();
            } else {
                return paymentRepository.paymentForCurrentUser(optionalUser.get().getUsername());
            }
        }
        return new ArrayList<>();
    }

    public List<Map<String, Object>> getWeekAnalytics() {
        String weekPaymentAnalyticQuery = "SELECT sum(price) amount, method FROM PAYMENT p WHERE p.payment_timestamp between date_sub(sysdate(), INTERVAL 7 DAY) and sysdate() group by p.method";
        log.info("QUERY : " + weekPaymentAnalyticQuery);
        return jdbcTemplate.queryForList(weekPaymentAnalyticQuery, new HashMap<>());
    }
}
