package com.example.mogak_payment.dto;

import com.example.mogak_payment.domain.PaymentResult;
import com.example.mogak_payment.domain.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePaymentResponse {

    private Integer code;
    private String payToken;
    private String errorCode;
    private String errorMessage;

    public PaymentResult toEntity() {
        return PaymentResult.builder()
                .payToken(payToken)
                .paymentStatus(code == 0 ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE)
                .errorCode(code != 0 ? errorCode : null)
                .errorMessage(code != 0? errorMessage : null)
                .createdAt(LocalDateTime.now())
                .build();
    }

}