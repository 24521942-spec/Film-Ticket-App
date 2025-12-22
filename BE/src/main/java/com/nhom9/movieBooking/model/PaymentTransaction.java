package com.nhom9.movieBooking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="payment_transaction")
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "gateway_transaction_ref")
    private String gatewayTransactionRef;

    public PaymentTransaction() {
    }

    public PaymentTransaction(BigDecimal amount, Booking booking, String gatewayTransactionRef, LocalDateTime paidAt, PaymentMethod paymentMethod, int transactionId, String transactionStatus) {
        this.amount = amount;
        this.booking = booking;
        this.gatewayTransactionRef = gatewayTransactionRef;
        this.paidAt = paidAt;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getGatewayTransactionRef() {
        return gatewayTransactionRef;
    }

    public void setGatewayTransactionRef(String gatewayTransactionRef) {
        this.gatewayTransactionRef = gatewayTransactionRef;
    }

   

    




}
