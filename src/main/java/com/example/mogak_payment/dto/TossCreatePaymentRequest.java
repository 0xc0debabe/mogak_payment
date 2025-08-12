package com.example.mogak_payment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TossCreatePaymentRequest {

    private String apiKey;
    private String orderNo;
    private String productDesc;
    private String retUrl;
    private String retCancelUrl;
    private Integer amount;
    private Integer amountTaxFree;

}
