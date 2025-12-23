package com.nhom9.movieBooking.dto;

public class PaymentSuccessRequest {
    private Integer paymentMethodId;
    private String gatewayRef;
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    public String getGatewayRef() {
        return gatewayRef;
    }
    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }

    
}
