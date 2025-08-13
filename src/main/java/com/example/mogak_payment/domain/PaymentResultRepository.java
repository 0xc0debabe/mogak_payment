package com.example.mogak_payment.domain;

import com.example.mogak_payment.domain.projection.RefundProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {


    Optional<RefundProjection> findByOrderNo(String orderNo);

}
