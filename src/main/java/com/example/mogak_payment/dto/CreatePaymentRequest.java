package com.example.mogak_payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {

    private String productDesc;
    private Integer amount;
    private Integer amountTaxFree;

}