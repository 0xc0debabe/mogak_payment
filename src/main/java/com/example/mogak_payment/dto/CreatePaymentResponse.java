package com.example.mogak_payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentResponse {

    private Integer code;
    private String payToken;
    private String checkoutPage;
    private String errorCode;
    private String errorMessage;

}