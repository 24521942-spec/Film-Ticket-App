package com.nhom9.movieBooking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private int paymentMethodId;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentTransaction> paymentTransactions;

    @Column(name = "payment_method_name", length = 100, nullable = false)
    private String paymentMethodName;

    @Column(name = "description_payment_method")
    private String descriptionPaymentMethod;

    @Column(name = "payment_method_status")
    private String paymentMethodStatus;

    public PaymentMethod() {}

    public PaymentMethod(String descriptionPaymentMethod, int paymentMethodId, String paymentMethodName, String paymentMethodStatus, List<PaymentTransaction> paymentTransactions) {
        this.descriptionPaymentMethod = descriptionPaymentMethod;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentMethodStatus = paymentMethodStatus;
        this.paymentTransactions = paymentTransactions;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public List<PaymentTransaction> getPaymentTransactions() {
        return paymentTransactions;
    }

    public void setPaymentTransactions(List<PaymentTransaction> paymentTransactions) {
        this.paymentTransactions = paymentTransactions;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getDescriptionPaymentMethod() {
        return descriptionPaymentMethod;
    }

    public void setDescriptionPaymentMethod(String descriptionPaymentMethod) {
        this.descriptionPaymentMethod = descriptionPaymentMethod;
    }

    public String getPaymentMethodStatus() {
        return paymentMethodStatus;
    }

    public void setPaymentMethodStatus(String paymentMethodStatus) {
        this.paymentMethodStatus = paymentMethodStatus;
    }

    
    


}
