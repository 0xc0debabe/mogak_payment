package com.example.mogak_payment.dto;

import com.example.mogak_payment.domain.RefundResult;
import lombok.Data;

@Data
public class TossRefundResponse {

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

    public RefundResult toEntity() {
        return RefundResult.builder()
                .code(this.code)
                .refundNo(this.refundNo)
                .refundableAmount(this.refundableAmount)
                .discountedAmount(this.discountedAmount)
                .paidAmount(this.paidAmount)
                .refundedAmount(this.refundedAmount)
                .refundedDiscountAmount(this.refundedDiscountAmount)
                .refundedPaidAmount(this.refundedPaidAmount)
                .approvalTime(this.approvalTime)
                .cashReceiptMgtKey(this.cashReceiptMgtKey)
                .payToken(this.payToken)
                .transactionId(this.transactionId)
                .cardMethodType(this.cardMethodType)
                .cardNumber(this.cardNumber)
                .cardUserType(this.cardUserType)
                .cardBinNumber(this.cardBinNumber)
                .cardNum4Print(this.cardNum4Print)
                .salesCheckLinkUrl(this.salesCheckLinkUrl)
                .accountBankCode(this.accountBankCode)
                .accountBankName(this.accountBankName)
                .accountNumber(this.accountNumber)
                .msg(this.msg)
                .errorCode(this.errorCode)
                .build();
    }
}