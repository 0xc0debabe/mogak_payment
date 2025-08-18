package com.example.mogak_payment.controller;

import com.example.mogak_payment.domain.RefundResult;
import com.example.mogak_payment.dto.*;
import com.example.mogak_payment.service.TossPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class TossPayController {

    private final TossPayService tossPayService;

    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentReq) {
        return ResponseEntity.ok(tossPayService.createPayment(createPaymentReq));
    }

    @GetMapping("/returl")
    public String returl() {
        return "success";
    }

    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> handleTossCallbackForm(CallBackRequest callBackRequest, @RequestParam(value = "memberId") Long memberId) {
        tossPayService.handlePaymentCallback(callBackRequest, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refunds")
    public ResponseEntity<RefundResponse> processRefund(@RequestBody RefundRequest request) {
        return ResponseEntity.ok(tossPayService.processRefund(request));
    }

    @GetMapping("/status")
    public ResponseEntity<PayStatusCheckResponse> statusCheck(@RequestBody PayStatusCheckRequest request) {
        return ResponseEntity.ok(tossPayService.checkStatus(request));
    }

}