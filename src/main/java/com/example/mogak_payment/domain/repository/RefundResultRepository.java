package com.example.mogak_payment.domain.repository;

import com.example.mogak_payment.domain.RefundResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundResultRepository extends JpaRepository<RefundResult, Long> {
}
