package com.example.mogak_payment.dto;

import com.example.mogak_payment.domain.PaymentResult;
import lombok.Data;

@Data
public class CallBackRequest {
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

    public PaymentResult toEntity(Long memberId) {
        return PaymentResult.builder()
                .status(status)
                .payToken(payToken)
                .orderNo(orderNo)
                .payMethod(payMethod)
                .amount(amount)
                .discountedAmount(discountedAmount)
                .paidAmount(paidAmount)
                .paidTs(paidTs)
                .transactionId(transactionId)
                .cardCompanyCode(cardCompanyCode)
                .cardAuthorizationNo(cardAuthorizationNo)
                .spreadOut(spreadOut)
                .noInterest(noInterest)
                .cardMethodType(cardMethodType)
                .cardNumber(cardNumber)
                .cardUserType(cardUserType)
                .cardBinNumber(cardBinNumber)
                .cardNum4Print(cardNum4Print)
                .salesCheckLinkUrl(salesCheckLinkUrl)
                .accountBankCode(accountBankCode)
                .accountBankName(accountBankName)
                .accountNumber(accountNumber)
                .paidPoint(paidPoint)
                .memberId(memberId)
                .build();
    }
}