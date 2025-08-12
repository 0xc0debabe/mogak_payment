package com.example.mogak_payment.service;

import com.example.mogak_payment.domain.PaymentResult;
import com.example.mogak_payment.domain.PaymentResultRepository;
import com.example.mogak_payment.dto.CreatePaymentRequest;
import com.example.mogak_payment.dto.CreatePaymentResponse;
import com.example.mogak_payment.dto.TossCreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TossPayService {

    private static final String RET_URL = "/success";
    private static final String RET_CANCEL_URL = "/cancel";

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
                .productDesc(createPaymentReq.getProductDesc())
                .amount(createPaymentReq.getAmount())
                .amountTaxFree(createPaymentReq.getAmountTaxFree())
                .build();

        CreatePaymentResponse res = restClient.post()
                .uri("/payments")
                .body(tossCreatePaymentRequest)
                .retrieve()
                .body(CreatePaymentResponse.class);

        PaymentResult paymentResult = Objects.requireNonNull(res).toEntity();
        paymentResultRepository.save(paymentResult);

        return res;
    }

}