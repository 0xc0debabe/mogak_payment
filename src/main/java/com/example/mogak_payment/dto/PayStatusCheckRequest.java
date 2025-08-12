package com.example.mogak_payment.dto;

import lombok.Getter;

@Getter
public class PayStatusCheckRequest {
    private String apiKey;
    private String payToken;
}