package com.nhom9.movieBooking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.FoodDto;
import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.model.Booking;
import com.nhom9.movieBooking.model.BookingFood;
import com.nhom9.movieBooking.model.BookingSeat;
import com.nhom9.movieBooking.model.Food;
import com.nhom9.movieBooking.model.Seat;
import com.nhom9.movieBooking.model.SeatHold;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.BookingFoodRepository;
import com.nhom9.movieBooking.repository.BookingRepository;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.repository.FoodRepository;
import com.nhom9.movieBooking.repository.SeatRepository;
import com.nhom9.movieBooking.repository.SeatholdRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowTimeRepository showtimeRepository;
    private final SeatholdRepository seatholdRepository;
    private final BookingFoodRepository bookingfoodRepository;
    private final BookingRepository bookingRepository;
    private final BookingseatRepository bookingseatRepository;
    private final SeatRepository seatRepository;
    private final FoodRepository foodRepository;

    public BookingServiceImpl(
            FoodRepository foodRepository,
            BookingRepository bookingRepository,
            BookingFoodRepository bookingfoodRepository,
            BookingseatRepository bookingseatRepository,
            SeatRepository seatRepository,
            SeatholdRepository seatholdRepository,
            ShowTimeRepository showtimeRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingfoodRepository = bookingfoodRepository;
        this.bookingseatRepository = bookingseatRepository;
        this.seatRepository = seatRepository;
        this.seatholdRepository = seatholdRepository;
        this.showtimeRepository = showtimeRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public BookingResponseDto checkoutFromHold(BookingRequestDto req) {
        Integer userId = req.getUserId();
        Integer showtimeId = req.getShowtimeId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        LocalDateTime now = LocalDateTime.now();

        // 1) Lấy các ghế đang HOLD còn hạn của user trong showtime
        List<SeatHold> holds = seatholdRepository
                .findByUserUserIdAndShowtimeShowTimeIdAndExpireAtAfter(userId, showtimeId, now);

        if (holds.isEmpty()) {
            throw new RuntimeException("No seats held or hold expired");
        }

        // 2) Validate seatIds gửi lên
        List<Integer> seatIds = req.getSeats();
        if (seatIds == null || seatIds.isEmpty()) {
            throw new RuntimeException("Seat list is empty");
        }

        Set<Integer> heldSeatIds = holds.stream()
                .map(h -> h.getSeat().getSeatId())
                .collect(Collectors.toSet());

        for (Integer seatId : seatIds) {
            if (!heldSeatIds.contains(seatId)) {
                throw new RuntimeException("Seat not held: " + seatId);
            }
        }

        // 3) Tạo BOOKING
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setCreatedAt(now);

        // DEMO: set PAID luôn
        booking.setStatusBooking("PAID");
        booking.setTotalTickets(seatIds.size());

        booking = bookingRepository.save(booking);

        // 4) BOOKING_SEAT
        BigDecimal seatTotal = BigDecimal.ZERO;
        BigDecimal seatPrice = showtime.getBasePrice();

        for (Integer seatId : seatIds) {

            // ✅ Check SOLD (đúng thứ tự tham số: showtimeId, seatId, status)
            boolean alreadySold = bookingseatRepository
                    .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBooking(showtimeId, seatId, "PAID");
            if (alreadySold) {
                throw new RuntimeException("Seat already sold: " + seatId);
            }

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            BookingSeat bs = new BookingSeat();
            bs.setBooking(booking);
            bs.setShowtime(showtime);
            bs.setSeat(seat);
            bs.setSeatPrice(seatPrice); // nhớ: BookingSeat phải có field + setter này

            bookingseatRepository.save(bs);
            seatTotal = seatTotal.add(seatPrice);
        }

        // 5) BOOKING_FOOD
        BigDecimal foodTotal = BigDecimal.ZERO;

        if (req.getFoodItems() != null) {
            for (FoodDto item : req.getFoodItems()) {
                Food food = foodRepository.findById(item.getFoodId())
                        .orElseThrow(() -> new RuntimeException("Food not found: " + item.getFoodId()));

                int qty = item.getQuantity();
                if (qty <= 0) continue;

                BigDecimal unitPrice = food.getUnitPrice();
                BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));

                BookingFood bf = new BookingFood();
                bf.setBooking(booking);
                bf.setFood(food);
                bf.setQuantity(qty);
                bf.setUnitPriceAtOrder(unitPrice);
                bf.setLineTotal(lineTotal);

                bookingfoodRepository.save(bf);
                foodTotal = foodTotal.add(lineTotal);
            }
        }

        BigDecimal totalPay = seatTotal.add(foodTotal);
        booking.setTotalPay(totalPay);
        bookingRepository.save(booking);

        // 6) Xoá HOLD sau khi đã checkout
        List<SeatHold> toDelete = holds.stream()
                .filter(h -> seatIds.contains(h.getSeat().getSeatId()))
                .toList();
        seatholdRepository.deleteAll(toDelete);

        // 7) ✅ Build List<SeatDto> theo BookingResponseDto của bạn
        List<SeatDto> seatDtos = seatIds.stream()
                .map(id -> {
                    Seat s = seatRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Seat not found"));
                    return new SeatDto(
                            s.getColLabel(),
                            s.getRowLabel(),
                            s.getSeatCode(),
                            s.getSeatId(),
                            s.getSeatType(),
                            SeatStatus.SOLD
                    );
                })
                .toList();

        return new BookingResponseDto(
                booking.getBookingId(),
                "PAID",
                seatDtos,
                showtimeId,
                booking.getStatusBooking(),
                userId
        );
    }
}
