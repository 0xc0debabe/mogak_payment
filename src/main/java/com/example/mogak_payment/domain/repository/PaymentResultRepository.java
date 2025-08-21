package com.example.mogak_payment.domain.repository;

import com.example.mogak_payment.domain.PaymentResult;
import com.example.mogak_payment.domain.projection.RefundProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {


    Optional<RefundProjection> findByOrderNo(String orderNo);

    Optional<PaymentResult> findByPayToken(String payToken);

    boolean existsByOrderNo(String orderNo);

}
