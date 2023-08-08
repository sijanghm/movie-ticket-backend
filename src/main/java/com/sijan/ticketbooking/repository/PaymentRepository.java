package com.sijan.ticketbooking.repository;

import com.sijan.ticketbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Query(value = "SELECT p FROM Payment p WHERE p.paidBy = :username ")
    List<Payment> paymentForCurrentUser(@Param("username") String username);
}
