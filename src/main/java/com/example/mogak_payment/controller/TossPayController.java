package com.example.mogak_payment.controller;

import com.example.mogak_payment.dto.CreatePaymentRequest;
import com.example.mogak_payment.dto.CreatePaymentResponse;
import com.example.mogak_payment.service.TossPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TossPayController {

    private final TossPayService tossPayService;

    @PostMapping("/create-payment")
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        CreatePaymentResponse response = tossPayService.createPayment(request);
        return ResponseEntity.ok(response);
    }

}
