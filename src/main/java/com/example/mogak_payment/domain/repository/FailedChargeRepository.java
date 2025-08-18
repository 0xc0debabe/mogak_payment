package com.example.mogak_payment.domain.repository;

import com.example.mogak_payment.domain.FailedCharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedChargeRepository extends JpaRepository<FailedCharge, Long> {
}
