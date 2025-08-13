package com.example.mogak_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossPayStatusCheckRequest {

    private String apiKey;
    private String orderNo;

}
