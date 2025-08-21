package com.example.mogak_payment.service;

import com.example.mogak_payment.domain.FailedCharge;
import com.example.mogak_payment.domain.RefundResult;
import com.example.mogak_payment.domain.repository.FailedChargeRepository;
import com.example.mogak_payment.domain.repository.PaymentResultRepository;
import com.example.mogak_payment.domain.repository.RefundResultRepository;
import com.example.mogak_payment.dto.*;
import com.example.mogak_payment.exception.ErrorCode;
import com.example.mogak_payment.exception.PayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class TossPayService {

    private static final String RET_URL = "https://api.mogak.kr/payment/returl";
    private static final String RET_CALLBACK = "https://api.mogak.kr/payment/callback";
    private static final String RET_CANCEL_URL = "https://api.mogak.kr/payment/cancel";

    @Qualifier(value = "tossRestClient")
    private final RestClient tossRestClient;
    @Qualifier(value = "mogakRestClient")
    private final RestClient mogakRestClient;

    private final PaymentResultRepository paymentResultRepository;
    private final RefundResultRepository refundResultRepository;
    private final FailedChargeRepository failedChargeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public TossPayService(
            @Qualifier("tossRestClient") RestClient tossRestClient,
            @Qualifier("mogakRestClient") RestClient mogakRestClient,
            PaymentResultRepository paymentResultRepository,
            RefundResultRepository refundResultRepository,
            FailedChargeRepository failedChargeRepository,
            RedisTemplate<String, String> redisTemplate
    ) {
        this.tossRestClient = tossRestClient;
        this.mogakRestClient = mogakRestClient;
        this.paymentResultRepository = paymentResultRepository;
        this.refundResultRepository = refundResultRepository;
        this.failedChargeRepository = failedChargeRepository;
        this.redisTemplate = redisTemplate;
    }

    @Value("${toss.secretKey}")
    private String apiKey;

    @Transactional(readOnly = true)
    public CreatePaymentResponse createPayment(CreatePaymentRequest createPaymentReq) {
        String memberIdParameter = "?memberId=" + createPaymentReq.getMemberId();
        TossCreatePaymentRequest tossCreatePaymentRequest = TossCreatePaymentRequest.builder()
                .apiKey(apiKey)
                .retUrl(RET_URL)
                .retCancelUrl(RET_CANCEL_URL)
                .resultCallback(RET_CALLBACK + memberIdParameter)
                .orderNo(UUID.randomUUID().toString())
                .autoExecute(true)
                .productDesc(createPaymentReq.getProductDesc())
                .amount(createPaymentReq.getAmount())
                .amountTaxFree(0)
                .build();

        return tossRestClient.post()
                .uri("/payments")
                .body(tossCreatePaymentRequest)
                .retrieve()
                .body(CreatePaymentResponse.class);
    }

    public void handlePaymentCallback(CallBackRequest callback, Long memberId) {
        log.info("Callback requested, memberId={}", memberId);
        if (isDuplicate(callback)) return;
        paymentResultRepository.save(callback.toEntity(memberId));
        chargeGamePoint(memberId, callback.getAmount(), callback.getOrderNo());
    }

    @Retryable(
            retryFor = { RestClientException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    private void chargeGamePoint(Long memberId, Integer amount, String orderNo) {
        log.info("ChargeGamePoint requested, memberId={}, amount={}, orderNo={}", memberId, amount, orderNo);
        ChargePointRequest request = new ChargePointRequest(memberId, amount, orderNo);

        mogakRestClient.post()
                .uri("/gamepoint/exchange")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    private boolean isDuplicate(CallBackRequest callback) {
        log.info("DuplicateCheck");

        if (paymentResultRepository.existsByOrderNo(callback.getOrderNo())) return true;
        Boolean isFirst = redisTemplate.opsForValue().setIfAbsent(callback.getOrderNo(), "lock", Duration.ofMinutes(3));
        return Boolean.FALSE.equals(isFirst);
    }

    @Recover
    public void recoverAfterRetries(RestClientException e, Long memberId, Integer amount, String payToken) {
        log.error("포인트 충전 API 최종 실패. payToken={}, memberId={}. Error: {}", payToken, memberId, e.getMessage());
        failedChargeRepository.save(new FailedCharge(null, memberId, amount, false));
     }

    @Transactional(readOnly = true)
    public PayStatusCheckResponse checkStatus(PayStatusCheckRequest request) {
        TossPayStatusCheckRequest checkRequest = new TossPayStatusCheckRequest(apiKey, request.getOrderNo());

        return tossRestClient.post()
                .uri("/status")
                .body(checkRequest)
                .retrieve()
                .body(PayStatusCheckResponse.class);
    }

    public RefundResponse processRefund(RefundRequest request) {
        RefundInfo info = paymentResultRepository.findByOrderNo(request.getOrderNo())
                .map(p -> RefundInfo.builder()
                        .payToken(p.getPayToken())
                        .amount(p.getAmount())
                        .build())
                .orElseThrow(() -> new PayException(ErrorCode.NOT_EXIST_PAY_INFO));

        TossRefundRequest refundRequest = TossRefundRequest.builder()
                .apiKey(apiKey)
                .payToken(info.getPayToken())
                .refundNo(UUID.randomUUID().toString())
                .idempotent(true)
                .reason(request.getReason())
                .amount(info.getAmount())
                .build();

        TossRefundResponse refundResponse = tossRestClient.post()
                .uri("/refunds")
                .body(refundRequest)
                .retrieve()
                .body(TossRefundResponse.class);

        RefundResult refundResult = Objects.requireNonNull(refundResponse).toEntity();
        refundResultRepository.save(Objects.requireNonNull(refundResult));
        boolean success = refundResult.getCode() == 0;
        return new RefundResponse(success, refundResult.getRefundedAmount(), success ? null : refundResult.getMsg());
    }

}