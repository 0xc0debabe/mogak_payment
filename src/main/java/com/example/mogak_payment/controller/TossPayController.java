package com.example.mogak_payment.controller;

import com.example.mogak_payment.dto.CallBackRequest;
import com.example.mogak_payment.dto.CreatePaymentRequest;
import com.example.mogak_payment.dto.CreatePaymentResponse;
import com.example.mogak_payment.service.TossPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mogak/payment")
@RequiredArgsConstructor
public class TossPayController {

    private final TossPayService tossPayService;

    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentReq) {
        return ResponseEntity.ok(tossPayService.createPayment(createPaymentReq));
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> handleTossCallback(@RequestBody CallBackRequest callBackRequest) {
        tossPayService.handlePaymentCallback(callBackRequest);
        return ResponseEntity.ok().build();
    }

}

