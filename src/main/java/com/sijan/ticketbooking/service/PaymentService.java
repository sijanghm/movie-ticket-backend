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

    public List<Map<String, Object>> getWeekAnalyticsByPaymentMethod() {
        String weekPaymentAnalyticQuery = "SELECT sum(price) amount, method FROM PAYMENT p WHERE p.payment_timestamp between date_sub(sysdate(), INTERVAL 7 DAY) and sysdate() group by p.method";
        log.info("QUERY : " + weekPaymentAnalyticQuery);
        return jdbcTemplate.queryForList(weekPaymentAnalyticQuery, new HashMap<>());
    }

    public List<Map<String, Object>> getWeekAnalyticByMovies() {
        String weekPaymentAnalyticQuery = """
        SELECT
        	amount,
        	movie_id,
        	movie_name FROM (
        	SELECT
        		sum(price) amount,
        		movie_id
        	FROM
        		payment p
        	where
        		p.payment_timestamp between date_sub(sysdate(), interval 7 day) and sysdate()
        	group by
        		movie_id
        		) r
        JOIN movies m ON
        	r.movie_id = m.id
        """;
        log.info("QUERY : " + weekPaymentAnalyticQuery);
        return jdbcTemplate.queryForList(weekPaymentAnalyticQuery, new HashMap<>());
    }

    public List<Map<String, Object>> getWeeklyAnalyticByDay() {
        String weekPaymentAnalyticQuery = """
            SELECT
            sum(p.price) amount,
            ELT(DAYOFWEEK(p.payment_timestamp), 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday') payday
            FROM
            payment p GROUP BY payday
		""";
        log.info("QUERY : " + weekPaymentAnalyticQuery);
        return jdbcTemplate.queryForList(weekPaymentAnalyticQuery, new HashMap<>());
    }
}
