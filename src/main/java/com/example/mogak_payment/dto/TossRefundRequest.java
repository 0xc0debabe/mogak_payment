package com.example.mogak_payment.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TossRefundRequest {

    private String apiKey;
    private String payToken;
    private String refundNo;
    private boolean idempotent;
    private String reason;
    private Integer amount;

}
