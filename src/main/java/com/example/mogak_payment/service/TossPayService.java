package com.example.mogak_payment.service;

import com.example.mogak_payment.domain.PaymentResultRepository;
import com.example.mogak_payment.domain.RefundResult;
import com.example.mogak_payment.domain.RefundResultRepository;
import com.example.mogak_payment.dto.*;
import com.example.mogak_payment.exception.ErrorCode;
import com.example.mogak_payment.exception.PayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossPayService {

    private static final String RET_URL = "https://api.mogak.kr/payment/returl";
    private static final String RET_CALLBACK = "https://api.mogak.kr/payment/callback";
    private static final String RET_CANCEL_URL = "https://api.mogak.kr/payment/cancel";

    private final RestClient restClient;
    private final PaymentResultRepository paymentResultRepository;
    private final RefundResultRepository refundResultRepository;

    @Value("${toss.secretKey}")
    private String apiKey;

    public CreatePaymentResponse createPayment(CreatePaymentRequest createPaymentReq) {

        TossCreatePaymentRequest tossCreatePaymentRequest = TossCreatePaymentRequest.builder()
                .apiKey(apiKey)
                .retUrl(RET_URL)
                .retCancelUrl(RET_CANCEL_URL)
                .resultCallback(RET_CALLBACK)
                .orderNo(UUID.randomUUID().toString())
                .autoExecute(true)
                .productDesc(createPaymentReq.getProductDesc())
                .amount(createPaymentReq.getAmount())
                .amountTaxFree(0)
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
        // mogak api로 넘겨줘야함
    }

    public PayStatusCheckResponse checkStatus(PayStatusCheckRequest request) {
        TossPayStatusCheckRequest checkRequest = new TossPayStatusCheckRequest(apiKey, request.getOrderNo());

        return restClient.post()
                .uri("/status")
                .body(checkRequest)
                .retrieve()
                .body(PayStatusCheckResponse.class);
    }

    public RefundResponse processRefund(RefundRequest request) {
        log.info("1");
        RefundInfo info = paymentResultRepository.findByOrderNo(request.getOrderNo())
                .map(p -> RefundInfo.builder()
                        .payToken(p.getPayToken())
                        .amount(p.getAmount())
                        .build())
                .orElseThrow(() -> new PayException(ErrorCode.NOT_EXIST_PAY_INFO));
        log.info("2");

        TossRefundRequest refundRequest = TossRefundRequest.builder()
                .apiKey(apiKey)
                .payToken(info.getPayToken())
                .refundNo(UUID.randomUUID().toString())
                .idempotent(true)
                .reason(request.getReason())
                .amount(info.getAmount())
                .build();
        log.info("3");
        RefundResult refundResult = restClient.post()
                .body(refundRequest)
                .retrieve()
                .body(RefundResult.class);
        log.info("4");
        refundResultRepository.save(Objects.requireNonNull(refundResult));
        boolean success = refundResult.getCode() == 0;
        log.info("5");
        return new RefundResponse(success, refundResult.getRefundedAmount(), success ? null : refundResult.getMsg());
    }

}