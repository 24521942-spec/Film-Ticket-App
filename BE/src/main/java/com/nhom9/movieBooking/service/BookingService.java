package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingBriefDto;
import com.nhom9.movieBooking.dto.BookingDetailDto;
import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewRequestDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewResponseDto;
import com.nhom9.movieBooking.dto.FakeBookingRequestDto;
import com.nhom9.movieBooking.dto.PaymentSuccessRequest;
import com.nhom9.movieBooking.dto.TicketDto;

@Service
public interface BookingService {
    BookingResponseDto checkoutFromHold(BookingRequestDto req);
    CheckoutPreviewResponseDto previewCheckout(Integer showtimeId, CheckoutPreviewRequestDto req);
    BookingResponseDto markPaid(Integer bookingId);
    BookingResponseDto paySuccess(Integer bookingId, PaymentSuccessRequest req);
    BookingResponseDto cancel(Integer bookingId);

    List<TicketDto> createTicketsAfterPaid(Integer bookingId);
    List<TicketDto> getTicketsByBooking(Integer bookingId);

    List<BookingBriefDto> getMyBookings();
    BookingDetailDto getBookingDetailFull(Integer bookingId);



    Object createFakeBooking(FakeBookingRequestDto req);
    

}
