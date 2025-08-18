package com.example.mogak_payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
    private String spreadOut; // 변경: Integer -> String
    private Boolean noInterest; // 가능은 하지만 String 처리 후 변환이 안전
    private String cardMethodType;
    private String cardNumber;
    private String cardUserType;
    private String cardBinNumber;
    private String cardNum4Print;
    private String salesCheckLinkUrl;
    private String accountBankCode;
    private String accountBankName;
    private String accountNumber;
    private Integer paidPoint; // optional, fadeout field

    private Long memberId;

}
