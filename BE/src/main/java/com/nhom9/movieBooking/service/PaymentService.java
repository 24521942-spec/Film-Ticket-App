package com.nhom9.movieBooking.service;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.PaymentQrResponse;

@Service
public interface  PaymentService {
    PaymentQrResponse generatePaymentQr(Integer bookingId);
    BookingResponseDto paymentSuccess(Integer bookingId);
    BookingResponseDto paymentFail(Integer bookingId);
}
