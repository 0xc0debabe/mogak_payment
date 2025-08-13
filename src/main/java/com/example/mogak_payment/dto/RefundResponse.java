package com.example.mogak_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {

    private boolean success;
    private Integer refundedAmount;
    private String message;

}