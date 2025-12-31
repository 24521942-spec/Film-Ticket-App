package com.nhom9.movieBooking.service.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.nhom9.movieBooking.model.Booking;
import com.nhom9.movieBooking.model.BookingFood;
import com.nhom9.movieBooking.model.BookingSeat;
import com.nhom9.movieBooking.model.Food;
import com.nhom9.movieBooking.model.Seat;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.model.Ticket;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.BookingFoodRepository;
import com.nhom9.movieBooking.repository.BookingRepository;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.repository.FoodRepository;
import com.nhom9.movieBooking.repository.SeatRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.repository.TicketRepository;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    private final TicketRepository ticketRepo;
    private final BookingRepository bookingRepo;
    private final BookingseatRepository bookingSeatRepo;

    private final BookingFoodRepository bookingFoodRepo;
    private final FoodRepository foodRepo;

    private final ShowTimeRepository showtimeRepo;
    private final SeatRepository seatRepo;
    private final UserRepository userRepo;

    public BookingServiceImpl(
            TicketRepository ticketRepo,
            BookingRepository bookingRepo,
            BookingseatRepository bookingSeatRepo,
            BookingFoodRepository bookingFoodRepo,
            FoodRepository foodRepo,
            ShowTimeRepository showtimeRepo,
            SeatRepository seatRepo,
            UserRepository userRepo
    ) {
        this.ticketRepo = ticketRepo;
        this.bookingRepo = bookingRepo;
        this.bookingSeatRepo = bookingSeatRepo;

        this.bookingFoodRepo = bookingFoodRepo;
        this.foodRepo = foodRepo;

        this.showtimeRepo = showtimeRepo;
        this.seatRepo = seatRepo;
        this.userRepo = userRepo;
    }

    // ===================== TICKETS =====================

    @Override
    public List<TicketDto> createTicketsAfterPaid(Integer bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        ShowTime bookingShowtime = booking.getShowtime();

        List<BookingSeat> bookingSeats = bookingSeatRepo.findByBookingBookingId(bookingId);
        if (bookingSeats == null || bookingSeats.isEmpty()) {
            throw new RuntimeException("No booking_seat rows for bookingId=" + bookingId);
        }

        List<TicketDto> result = new ArrayList<>();

        for (BookingSeat bs : bookingSeats) {
            Seat seat = bs.getSeat();
            ShowTime showtime = (bs.getShowtime() != null) ? bs.getShowtime() : bookingShowtime;

            if (seat == null) continue;
            if (showtime == null) throw new RuntimeException("Missing showtime for bookingId=" + bookingId);

            String qrText = "BOOKING=" + bookingId; // ✅ chỉ theo bookingId
            String qrUrl = buildQrImageUrl(qrText);
            

            Ticket ticket = new Ticket();
            ticket.setBooking(booking);
            ticket.setShowtime(showtime);
            ticket.setSeat(seat);
            ticket.setQrCode(qrUrl);

            BigDecimal price = bs.getSeatPrice();
            if (price == null) price = showtime.getBasePrice();
            ticket.setPrice(price == null ? BigDecimal.ZERO : price);

            ticket.setStatusTicket("PAID");
            ticket.setCreatedAt(LocalDateTime.now());
            ticket.setQrCode(qrUrl);

            Ticket saved = ticketRepo.save(ticket);

            result.add(new TicketDto(
                    saved.getBooking().getBookingId(),
                    saved.getQrCode(),
                    saved.getSeat().getSeatId(),
                    saved.getStatusTicket(),
                    saved.getTicketId()
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
        return "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + encoded;
    }

    // ===================== PAYMENT =====================

    @Override
    public BookingResponseDto paySuccess(Integer bookingId, PaymentSuccessRequest req) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        trySetBookingStatus(booking, "PAID");
        trySetBookingPaidTime(booking, LocalDateTime.now());
        bookingRepo.save(booking);

        List<Ticket> existing = ticketRepo.findByBookingBookingId(bookingId);
        if (existing == null || existing.isEmpty()) {
            createTicketsAfterPaid(bookingId);
        }

        BookingResponseDto res = new BookingResponseDto();
        try {
            res.getClass().getMethod("setBookingId", Integer.class).invoke(res, bookingId);
        } catch (Exception ignore) {}
        try {
            res.getClass().getMethod("setStatus", String.class).invoke(res, "PAID");
        } catch (Exception ignore) {}
        return res;
    }

    @Override
    public BookingResponseDto markPaid(Integer bookingId) {
        return paySuccess(bookingId, null);
    }

    @Override
    public BookingResponseDto cancel(Integer bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        trySetBookingStatus(booking, "CANCELLED");
        bookingRepo.save(booking);

        BookingResponseDto res = new BookingResponseDto();
        try {
            res.getClass().getMethod("setBookingId", Integer.class).invoke(res, bookingId);
        } catch (Exception ignore) {}
        try {
            res.getClass().getMethod("setStatus", String.class).invoke(res, "CANCELLED");
        } catch (Exception ignore) {}
        return res;
    }

    // ===================== MY BOOKINGS =====================

    @Override
    public List<BookingBriefDto> getMyBookings() {
        String identity = getCurrentIdentity();
        List<Booking> all = bookingRepo.findAll();
        List<BookingBriefDto> out = new ArrayList<>();

        for (Booking b : all) {
            String bookingIdentity = tryGetBookingIdentity(b);
            if (identity != null && bookingIdentity != null) {
                if (!identity.equalsIgnoreCase(bookingIdentity)) continue;
            }
            out.add(mapToBriefDto(b));
        }
        return out;
    }

    // ✅ DETAIL: trả seats + foods + totals để FE “xem lại vé”
    // ✅ detail đầy đủ: seats + foods + tickets(qr)
    @Override
    public BookingDetailDto getBookingDetailFull(Integer bookingId) {
        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        BookingDetailDto dto = new BookingDetailDto();
        dto.bookingId = b.getBookingId();
        dto.status = tryGetBookingStatus(b);

        ShowTime st = b.getShowtime();
        if (st != null) {
            dto.showtimeId = safeInt(st, "getShowtimeId");
            dto.cinemaName = tryGetShowtimeCinemaName(st);
            dto.roomName   = tryGetShowtimeCinemaName(st);
            dto.filmTitle  = tryGetShowtimeFilmTitle(st);
            dto.posterUrl  = tryGetShowtimePosterUrl(st);
            dto.showTime   = tryGetShowtimeCinemaName(st);
        }

        // ✅ QR cố định theo bookingId (không cần ticketRepo cũng được)
        dto.qrUrl = buildQrImageUrl("BOOKING=" + bookingId);

        // ===== SEATS =====
        BigDecimal ticketTotal = BigDecimal.ZERO;
        List<BookingSeat> bss = bookingSeatRepo.findByBookingBookingId(bookingId);
        if (bss != null) {
            for (BookingSeat bs : bss) {
                Seat s = bs.getSeat();

                BookingDetailDto.SeatLine line = new BookingDetailDto.SeatLine();
                line.seatId   = (s != null) ? s.getSeatId() : null;
                line.seatCode = (s != null) ? s.getSeatCode() : null;
                line.seatType = (s != null && s.getSeatType() != null) ? s.getSeatType().toString() : null;

                BigDecimal price = bs.getSeatPrice();
                if (price == null && st != null) price = st.getBasePrice();
                if (price == null) price = BigDecimal.ZERO;

                line.price = price;
                ticketTotal = ticketTotal.add(price);

                dto.seats.add(line);
            }
        }

        // ===== FOODS =====
        BigDecimal foodTotal = BigDecimal.ZERO;
        List<BookingFood> bfs = bookingFoodRepo.findByBookingBookingId(bookingId);
        if (bfs != null) {
            for (BookingFood bf : bfs) {
                Food f = bf.getFood();
                Integer qty = bf.getQuantity();
                if (qty == null) qty = 0;

                BigDecimal unitPrice = BigDecimal.ZERO;
                if (f != null && f.getUnitPrice() != null) { // nếu Food không có getUnitPrice thì đổi getter đúng của bạn
                    unitPrice = f.getUnitPrice();
                }

                BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));

                BookingDetailDto.FoodLine fl = new BookingDetailDto.FoodLine();
                fl.foodId = (f != null) ? f.getFoodId() : null;
                fl.foodName = (f != null) ? f.getFoodName() : null;
                fl.quantity = qty;
                fl.unitPrice = unitPrice;
                fl.lineTotal = lineTotal;

                foodTotal = foodTotal.add(lineTotal);
                dto.foods.add(fl);
            }
        }

        dto.ticketTotal = ticketTotal;
        dto.foodTotal = foodTotal;
        dto.totalPay = ticketTotal.add(foodTotal);

        return dto;
    }

    private Integer safeInt(Object obj, String methodName) {
        try {
            Object v = obj.getClass().getMethod(methodName).invoke(obj);
            if (v == null) return null;
            if (v instanceof Integer) return (Integer) v;
            return Integer.valueOf(v.toString());
        } catch (Exception e) {
            return null;
        }
    }



    private BookingBriefDto mapToBriefDto(Booking b) {
        Integer id = b.getBookingId();
        ShowTime st = b.getShowtime();

        String filmTitle = "";
        String posterUrl = "";
        String cinemaName = "";
        String showTime = "";
        String status = tryGetBookingStatus(b);

        if (st != null) {
            Object startTime = getByReflect(st, "getStartTime");
            showTime = (startTime == null) ? "" : startTime.toString();

            cinemaName = tryGetShowtimeCinemaName(st);
            filmTitle = tryGetShowtimeFilmTitle(st);
            posterUrl = tryGetShowtimePosterUrl(st);
        }

        return new BookingBriefDto(id, cinemaName, filmTitle, posterUrl, showTime, status);
    }

    // ===================== FAKE BOOKING =====================

    @Override
    public Object createFakeBooking(FakeBookingRequestDto req) {
        if (req == null || req.getShowtimeId() == null || req.getSeatIds() == null || req.getSeatIds().isEmpty()) {
            throw new RuntimeException("showtimeId & seatIds are required");
        }

        ShowTime showtime = showtimeRepo.findById(req.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found: " + req.getShowtimeId()));

        // ƯU TIÊN: nếu FE gửi userId thì lấy theo userId
        User user = null;
        try {
            if (req.getUserId() != null) {
                user = userRepo.findById(req.getUserId()).orElse(null);
            }
        } catch (Exception ignore) {}

        // fallback: từ JWT email
        if (user == null) {
            String email = getCurrentIdentity();
            if (email != null) user = userRepo.findByEmail(email).orElse(null);
        }

        Booking booking = new Booking();
        booking.setShowtime(showtime);
        if (user != null) booking.setUser(user);

        // ✅ statusBooking để match entity bạn đang dùng
        trySetBookingStatus(booking, "HOLD");
        try {
            booking.getClass().getMethod("setCreatedAt", LocalDateTime.class).invoke(booking, LocalDateTime.now());
        } catch (Exception ignore) {}

        Booking savedBooking = bookingRepo.save(booking);

        // seats
        for (Integer seatId : req.getSeatIds()) {
            Seat seat = seatRepo.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            BookingSeat bs = new BookingSeat();
            bs.setBooking(savedBooking);
            bs.setShowtime(showtime);
            bs.setSeat(seat);
            bs.setSeatPrice(showtime.getBasePrice());

            bookingSeatRepo.save(bs);
        }

        // ✅ foods: LƯU booking_food
        if (req.getFoods() != null) {
            for (var it : req.getFoods()) {
                if (it == null) continue;
                if (it.getFoodId() == null) continue;
                if (it.getQuantity() == null || it.getQuantity() <= 0) continue;

                Food food = foodRepo.findById(it.getFoodId())
                        .orElseThrow(() -> new RuntimeException("Food not found: " + it.getFoodId()));

                BookingFood bf = new BookingFood();
                bf.setBooking(savedBooking);
                bf.setFood(food);
                bf.setQuantity(it.getQuantity());

                BigDecimal unit = safeBigDecimal(getByReflect(food, "getUnitPrice"));
                if (unit == null) unit = BigDecimal.ZERO;
                bf.setUnitPriceAtOrder(unit);

                bookingFoodRepo.save(bf);
            }
        }

        String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data="
                + URLEncoder.encode("FAKE_MOMO_BOOKING_" + savedBooking.getBookingId(), StandardCharsets.UTF_8);

        return java.util.Map.of(
                "bookingId", savedBooking.getBookingId(),
                "qrUrl", qrUrl
        );
    }

    // ===================== HELPERS =====================

    private String getCurrentIdentity() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) return null;
            return auth.getName();
        } catch (Exception e) {
            return null;
        }
    }

    private String tryGetBookingIdentity(Booking booking) {
        try {
            Object user = booking.getClass().getMethod("getUser").invoke(booking);
            if (user != null) {
                Object email = getByReflect(user, "getEmail");
                if (email != null) return email.toString();
                Object username = getByReflect(user, "getUsername");
                if (username != null) return username.toString();
            }
        } catch (Exception ignore) {}

        Object email2 = getByReflect(booking, "getUserEmail");
        return email2 == null ? null : email2.toString();
    }

    // ✅ FIX: thêm setStatusBooking
    private void trySetBookingStatus(Booking booking, String status) {
        try { booking.getClass().getMethod("setStatus", String.class).invoke(booking, status); return; } catch (Exception ignore) {}
        try { booking.getClass().getMethod("setBookingStatus", String.class).invoke(booking, status); return; } catch (Exception ignore) {}
        try { booking.getClass().getMethod("setStatusBooking", String.class).invoke(booking, status); return; } catch (Exception ignore) {}
        try { booking.getClass().getMethod("setPaymentStatus", String.class).invoke(booking, status); } catch (Exception ignore) {}
    }

    private void trySetBookingPaidTime(Booking booking, LocalDateTime paidAt) {
        try { booking.getClass().getMethod("setPaidAt", LocalDateTime.class).invoke(booking, paidAt); return; } catch (Exception ignore) {}
        try { booking.getClass().getMethod("setPaidTime", LocalDateTime.class).invoke(booking, paidAt); return; } catch (Exception ignore) {}
        try { booking.getClass().getMethod("setUpdatedAt", LocalDateTime.class).invoke(booking, paidAt); } catch (Exception ignore) {}
    }

    // ✅ FIX: thêm getStatusBooking
    private String tryGetBookingStatus(Booking booking) {
        Object v;
        v = getByReflect(booking, "getStatus"); if (v != null) return v.toString();
        v = getByReflect(booking, "getBookingStatus"); if (v != null) return v.toString();
        v = getByReflect(booking, "getStatusBooking"); if (v != null) return v.toString();
        v = getByReflect(booking, "getPaymentStatus"); if (v != null) return v.toString();
        return "";
    }

    private String tryGetShowtimeCinemaName(ShowTime st) {
        Object v = getByReflect(st, "getCinemaName");
        if (v != null) return v.toString();

        Object cinema = getByReflect(st, "getCinema");
        if (cinema != null) {
            v = getByReflect(cinema, "getCinemaName");
            if (v != null) return v.toString();
            v = getByReflect(cinema, "getName");
            if (v != null) return v.toString();
        }
        return "";
    }

    private String tryGetShowtimeFilmTitle(ShowTime st) {
        Object v = getByReflect(st, "getFilmTitle");
        if (v != null) return v.toString();

        Object film = getByReflect(st, "getFilm");
        if (film != null) {
            v = getByReflect(film, "getTitle");
            if (v != null) return v.toString();
        }
        return "";
    }

    private String tryGetShowtimePosterUrl(ShowTime st) {
        Object v = getByReflect(st, "getPosterUrl");
        if (v != null) return v.toString();

        Object film = getByReflect(st, "getFilm");
        if (film != null) {
            v = getByReflect(film, "getPosterUrl");
            if (v != null) return v.toString();
            v = getByReflect(film, "getPoster");
            if (v != null) return v.toString();
        }
        return "";
    }

    private Object getByReflect(Object obj, String method) {
        if (obj == null) return null;
        try {
            return obj.getClass().getMethod(method).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    private String safeStr(Object o) { return o == null ? "" : o.toString(); }

    private Integer safeInt(Object o) {
        try { return o == null ? null : Integer.parseInt(o.toString()); }
        catch (Exception e) { return null; }
    }

    private BigDecimal safeBigDecimal(Object o) {
        try { return o == null ? null : new BigDecimal(o.toString()); }
        catch (Exception e) { return null; }
    }

    // ===================== NOT IMPLEMENTED YET =====================

    @Override
    public BookingResponseDto checkoutFromHold(BookingRequestDto req) {
        throw new UnsupportedOperationException("Unimplemented method 'checkoutFromHold'");
    }

    @Override
    public CheckoutPreviewResponseDto previewCheckout(Integer showtimeId, CheckoutPreviewRequestDto req) {
        throw new UnsupportedOperationException("Unimplemented method 'previewCheckout'");
    }
}
