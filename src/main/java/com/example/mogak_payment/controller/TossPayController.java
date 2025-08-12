package com.example.mogak_payment.controller;

import com.example.mogak_payment.dto.CallBackRequest;
import com.example.mogak_payment.dto.CreatePaymentRequest;
import com.example.mogak_payment.dto.CreatePaymentResponse;
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

    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> handleTossCallback(@RequestBody CallBackRequest callBackRequest) {
        tossPayService.handlePaymentCallback1(callBackRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> handleTossCallbackForm(CallBackRequest callBackRequest) {
        tossPayService.handlePaymentCallback2(callBackRequest);
        return ResponseEntity.ok().build();
    }

}

