package com.nhom9.movieBooking.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.FakeBookingResponse;
import com.nhom9.movieBooking.dto.PaymentQrResponse;
import com.nhom9.movieBooking.service.impl.PaymentServiceImpl;

@RestController
@RequestMapping("/api/bookings")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{bookingId}/payment/qr")
    public ResponseEntity<PaymentQrResponse> getPaymentQr(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.generatePaymentQr(bookingId));
    }

    @PostMapping("/{bookingId}/payment/success")
    public ResponseEntity<BookingResponseDto> paymentSuccess(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.paymentSuccess(bookingId));
    }

    @PostMapping("/{bookingId}/payment/fail")
    public ResponseEntity<BookingResponseDto> paymentFail(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.paymentFail(bookingId));
    }

    // @PostMapping("/fake")
    // public ResponseEntity<FakeBookingResponse> createFakeBooking() {
    //     int bookingId = ThreadLocalRandom.current().nextInt(10000, 99999);

    //     // QR fake: bạn có thể đổi text cho đẹp
    //     String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=FAKE_MOMO_BOOKING_" + bookingId;

    //     return ResponseEntity.ok(new FakeBookingResponse(bookingId, qrUrl));
    // }
}
