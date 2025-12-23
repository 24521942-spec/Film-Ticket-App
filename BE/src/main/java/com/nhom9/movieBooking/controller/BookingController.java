package com.nhom9.movieBooking.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.PaymentSuccessRequest;
import com.nhom9.movieBooking.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/checkout")
    public BookingResponseDto checkout(@RequestBody BookingRequestDto req) {
        return bookingService.checkoutFromHold(req);
    }

    @PutMapping("/{bookingId}/pay-success")
    public BookingResponseDto paySuccess(@PathVariable Integer bookingId) {
        return bookingService.markPaid(bookingId);
    }

    @PostMapping("/{bookingId}/pay/success")
    public BookingResponseDto paySuccess(
            @PathVariable Integer bookingId,
            @RequestBody(required = false) PaymentSuccessRequest req
    ) {
        return bookingService.paySuccess(bookingId, req);
    }

    @PostMapping("/{bookingId}/cancel")
    public BookingResponseDto cancel(@PathVariable Integer bookingId) {
        return bookingService.cancel(bookingId);
    }
}
