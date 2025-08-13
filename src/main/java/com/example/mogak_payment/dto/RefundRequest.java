package com.example.mogak_payment.dto;

import lombok.Getter;

@Getter
public class RefundRequest {

    private String orderNo;
    private String reason;

}