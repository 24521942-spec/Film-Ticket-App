package com.nhom9.movieBooking.service.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhom9.movieBooking.dto.TicketDto;
import com.nhom9.movieBooking.model.Booking;
import com.nhom9.movieBooking.model.BookingSeat;
import com.nhom9.movieBooking.model.Seat;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.model.Ticket;
import com.nhom9.movieBooking.repository.BookingRepository;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.repository.TicketRepository;
import com.nhom9.movieBooking.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final BookingRepository bookingRepo;
    private final BookingseatRepository bookingSeatRepo;


    public TicketServiceImpl(
            TicketRepository ticketRepo,
            BookingRepository bookingRepo,
            BookingseatRepository bookingSeatRepo,
            TicketRepository ticketRepository
    ) {
        this.ticketRepo = ticketRepo;
        this.bookingRepo = bookingRepo;
        this.bookingSeatRepo = bookingSeatRepo;
    }

    @Override
    @Transactional
    public List<TicketDto> createTicketsAfterPaid(Integer bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        ShowTime showtime = booking.getShowtime();
        if (showtime == null) throw new RuntimeException("Booking has no showtime");

        // ✅ Lấy ghế từ bảng booking_seat
        List<BookingSeat> bookingSeats = bookingSeatRepo.findByBookingBookingId(bookingId);
        if (bookingSeats == null || bookingSeats.isEmpty()) {
            throw new RuntimeException("No seats found in booking_seat for bookingId=" + bookingId);
        }

        List<TicketDto> result = new ArrayList<>();

        for (BookingSeat bs : bookingSeats) {
            Seat seat = bs.getSeat();
            if (seat == null) continue;

            String qrText = "BOOKING=" + bookingId + "|SEAT=" + seat.getSeatId();
            String qrUrl = buildQrImageUrl(qrText);

            Ticket ticket = new Ticket();
            ticket.setBooking(booking);
            ticket.setShowtime(showtime);
            ticket.setSeat(seat);
            ticket.setQrCode(qrUrl);

            // ✅ Giá: ưu tiên seatPrice ở booking_seat, fallback basePrice showtime
            BigDecimal price = bs.getSeatPrice();
            if (price == null) price = showtime.getBasePrice(); // basePrice của bạn là BigDecimal
            ticket.setPrice(price);

            ticket.setStatusTicket("PAID");
            ticket.setCreatedAt(LocalDateTime.now());

            Ticket saved = ticketRepo.save(ticket);

            result.add(new TicketDto(
                    saved.getBooking().getBookingId(), // bookingId
                    saved.getQrCode(),                 // qrCode
                    saved.getSeat().getSeatId(),       // seatId
                    saved.getStatusTicket(),           // status
                    saved.getTicketId()                // ticketId
            ));
        }

        return result;
    }

    @Override
    public List<TicketDto> getTicketsByBooking(Integer bookingId) {
        List<TicketDto> out = new ArrayList<>();
        List<Ticket> tickets = ticketRepo.findByBookingBookingId(bookingId);

        for (Ticket t : tickets) {
            out.add(new TicketDto(
                    t.getBooking().getBookingId(),
                    t.getQrCode(),
                    t.getSeat().getSeatId(),
                    t.getStatusTicket(),
                    t.getTicketId()
            ));
        }
        return out;
    }

    private String buildQrImageUrl(String data) {
        String encoded = URLEncoder.encode(data, StandardCharsets.UTF_8);
        // URL này trả về ẢNH QR (png) trực tiếp
        return "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + encoded;
    }
    @Override
    public List<TicketDto> getTicketsByUser(Integer userId) {
        return ticketRepo.findTicketDtosByUserId(userId);
    }

}
