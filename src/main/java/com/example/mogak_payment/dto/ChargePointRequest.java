package com.example.mogak_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChargePointRequest {

    private Long memberId;
    private Integer amount;

}