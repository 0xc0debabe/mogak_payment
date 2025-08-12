package com.example.mogak_payment.service;

import com.example.mogak_payment.domain.PaymentResultRepository;
import com.example.mogak_payment.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossPayService {

    private static final String RET_URL = "https://api.mogak.kr/payment/callback";
    private static final String RET_CANCEL_URL = "https://api.mogak.kr/payment/cancel";

    private final RestClient restClient;
    private final PaymentResultRepository paymentResultRepository;

    @Value("${toss.secretKey}")
    private String apiKey;

    public CreatePaymentResponse createPayment(CreatePaymentRequest createPaymentReq) {

        TossCreatePaymentRequest tossCreatePaymentRequest = TossCreatePaymentRequest.builder()
                .apiKey(apiKey)
                .retUrl(RET_URL)
                .retCancelUrl(RET_CANCEL_URL)
                .orderNo(UUID.randomUUID().toString())
                .autoExecute(true)
                .productDesc(createPaymentReq.getProductDesc())
                .amount(createPaymentReq.getAmount())
                .amountTaxFree(createPaymentReq.getAmountTaxFree())
                .build();

        return restClient.post()
                .uri("/payments")
                .body(tossCreatePaymentRequest)
                .retrieve()
                .body(CreatePaymentResponse.class);
    }

    public void handlePaymentCallback(CallBackRequest callback) {
        log.info("handlePaymentCallback");
        paymentResultRepository.save(callback.toEntity());
    }

    public PayStatusCheckResponse checkStatus(PayStatusCheckRequest request) {
        return restClient.post()
                .uri("/status")
                .body(request)
                .retrieve()
                .body(PayStatusCheckResponse.class);
    }

}