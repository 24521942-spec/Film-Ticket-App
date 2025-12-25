package com.nhom9.movieBooking.service;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewRequestDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewResponseDto;
import com.nhom9.movieBooking.dto.PaymentSuccessRequest;

@Service
public interface BookingService {
    BookingResponseDto checkoutFromHold(BookingRequestDto req);
    CheckoutPreviewResponseDto previewCheckout(Integer showtimeId, CheckoutPreviewRequestDto req);
    BookingResponseDto markPaid(Integer bookingId);
    BookingResponseDto paySuccess(Integer bookingId, PaymentSuccessRequest req);
    BookingResponseDto cancel(Integer bookingId);
    

}
