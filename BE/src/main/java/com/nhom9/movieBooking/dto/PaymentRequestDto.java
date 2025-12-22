package com.nhom9.movieBooking.dto;

public class PaymentRequestDto {
    private Integer paymentMethodId;   
    private Double amount;             
    private String paymentDetails; 

    public PaymentRequestDto(Double amount, String paymentDetails, Integer paymentMethodId) {
        this.amount = amount;
        this.paymentDetails = paymentDetails;
        this.paymentMethodId = paymentMethodId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }


}
