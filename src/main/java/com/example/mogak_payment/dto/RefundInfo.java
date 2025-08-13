package com.example.mogak_payment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefundInfo {

    private String payToken;
    private Integer amount;
}
