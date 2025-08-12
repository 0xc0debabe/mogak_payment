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

    public PaymentResult toEntity() {
        return PaymentResult.builder()
                .status(this.status)
                .payToken(this.payToken)
                .orderNo(this.orderNo)
                .payMethod(this.payMethod)
                .amount(this.amount)
                .discountedAmount(this.discountedAmount)
                .paidAmount(this.paidAmount)
                .paidTs(this.paidTs)
                .transactionId(this.transactionId)
                .cardCompanyCode(this.cardCompanyCode)
                .cardAuthorizationNo(this.cardAuthorizationNo)
                .spreadOut(this.spreadOut)
                .noInterest(this.noInterest)
                .cardMethodType(this.cardMethodType)
                .cardNumber(this.cardNumber)
                .cardUserType(this.cardUserType)
                .cardBinNumber(this.cardBinNumber)
                .cardNum4Print(this.cardNum4Print)
                .salesCheckLinkUrl(this.salesCheckLinkUrl)
                .accountBankCode(this.accountBankCode)
                .accountBankName(this.accountBankName)
                .accountNumber(this.accountNumber)
                .build();
    }
}