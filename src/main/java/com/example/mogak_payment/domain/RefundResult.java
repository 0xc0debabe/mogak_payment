package com.example.mogak_payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class RefundResult {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;
    private String refundNo;
    private Integer refundableAmount;
    private Integer discountedAmount;
    private Integer paidAmount;
    private Integer refundedAmount;
    private Integer refundedDiscountAmount;
    private Integer refundedPaidAmount;
    private String approvalTime;
    private String cashReceiptMgtKey;
    private String payToken;
    private String transactionId;
    private String cardMethodType;
    private String cardNumber;
    private String cardUserType;
    private String cardBinNumber;
    private String cardNum4Print;
    private String salesCheckLinkUrl;
    private String accountBankCode;
    private String accountBankName;
    private String accountNumber;
    private String msg;
    private String errorCode;

}
