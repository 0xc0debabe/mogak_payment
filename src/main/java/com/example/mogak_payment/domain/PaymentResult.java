package com.example.mogak_payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentResult {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String payToken;
    private String orderNo;
    private String payMethod;
    private Integer amount;
    private Integer discountedAmount;
    private Integer paidAmount;
    private String paidTs;
    private String transactionId;
    private Integer cardCompanyCode;
    private String cardAuthorizationNo;
    private Integer spreadOut;
    private Boolean noInterest;
    private String cardMethodType;
    private String cardNumber;
    private String cardUserType;
    private String cardBinNumber;
    private String cardNum4Print;
    private String salesCheckLinkUrl;
    private String accountBankCode;
    private String accountBankName;
    private String accountNumber;

}
