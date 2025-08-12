package com.example.mogak_payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/payment/test")
    public String test() {
        return "test";
    }
}
