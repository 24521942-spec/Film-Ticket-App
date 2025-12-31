package com.nhom9.movieBooking.dto;

public class PaymentResponseDto {
    private Integer transactionId;      
    private String status;             
    private Double amount;                
    private String paymentMethod;        
    private String gatewayTransactionRef;

    public PaymentResponseDto(Double amount, String gatewayTransactionRef, String paymentMethod, String status, Integer transactionId) {
        this.amount = amount;
        this.gatewayTransactionRef = gatewayTransactionRef;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getGatewayTransactionRef() {
        return gatewayTransactionRef;
    }

    public void setGatewayTransactionRef(String gatewayTransactionRef) {
        this.gatewayTransactionRef = gatewayTransactionRef;
    }


}
