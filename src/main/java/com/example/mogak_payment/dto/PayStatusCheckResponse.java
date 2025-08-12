package com.example.mogak_payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayStatusCheckResponse {
    private int code;              // 응답코드
    private String mode;           // 결제환경 (LIVE, TEST)
    private String payToken;       // 토스페이 토큰
    private String payStatus;      // 결제 상태
    private String orderNo;        // 주문번호
    private String payMethod;      // 결제수단
    private Integer amount;        // 총 결제 금액
    private Integer discountedAmount; // 할인된 금액
    private Integer discountAmountV2; // 즉시 할인 적용 금액
    private Integer paidPointV2;   // 토스 포인트 사용금액
    private Integer paidAmount;    // 지불수단 승인금액
    private Integer refundableAmount; // 환불 가능 잔액
    private Integer amountTaxFree;    // 비과세 금액
    private Integer amountTaxable;    // 과세 금액
    private Integer amountVat;        // 부가세 금액
    private Integer amountServiceFee; // 봉사료
    private Integer disposableCupDeposit; // 일회용 컵 보증금
    private String accountBankCode;  // 은행 코드 (토스머니)
    private String accountBankName;  // 은행 명
    private String accountNumber;    // 계좌번호 (일부 마스킹)

    private CardInfo card;           // 카드 정보 (카드 결제 시만)

    private List<Transaction> transactions; // 거래 트랜잭션 리스트

    @Data
    public static class CardInfo {
        private Boolean noInterest;       // 무이자 여부
        private Integer spreadOut;        // 카드 할부개월
        private String cardCompanyName;   // 카드사명
        private Integer cardCompanyCode;  // 카드사 코드
        private String cardAuthorizationNo; // 카드 승인번호
        private String cardMethodType;    // 카드 타입 (CREDIT, CHECK, PREPAYMENT)
        private String cardUserType;      // 카드 사용자 구분
        private String cardNumber;        // 마스킹 카드번호
        private String cardBinNumber;     // 카드 BIN 번호
        private String cardNum4Print;     // 카드 끝 4자리
        private String salesCheckLinkUrl; // 매출전표 호출 URL
    }

    @Data
    public static class Transaction {
        private String stepType;         // 거래 타입 (PAY, REFUND)
        private String transactionId;    // 거래 트랜잭션 아이디
        private Integer transactionAmount;   // 거래 금액 (환불 시 음수)
        private Integer discountedAmount;    // 할인 금액
        private Integer paidAmount;           // 지불수단 금액
        private String regTs;             // 요청 처리 시간
        private String createdTs;         // 최초 결제 요청 시간
        private String paidTs;            // 결제 완료 처리 시간
    }
}