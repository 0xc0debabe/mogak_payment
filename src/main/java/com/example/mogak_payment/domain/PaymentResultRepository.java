package com.example.mogak_payment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {
}
