package com.example.ticketbookingapp.network.dto;

public class MomoPaymentResponse {
    public boolean success;
    public String transactionId;

    // 1 trong 2 cái dưới tùy BE bạn trả:
    public String qrUrl;     // URL ảnh QR
    public String qrContent; // raw string để FE tự generate QR

    public String message;

    public MomoPaymentResponse(boolean success, String transactionId, String qrUrl, String qrContent, String message) {
        this.success = success;
        this.transactionId = transactionId;
        this.qrUrl = qrUrl;
        this.qrContent = qrContent;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
