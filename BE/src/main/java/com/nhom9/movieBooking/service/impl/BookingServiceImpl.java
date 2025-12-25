package com.nhom9.movieBooking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewRequestDto;
import com.nhom9.movieBooking.dto.CheckoutPreviewResponseDto;
import com.nhom9.movieBooking.dto.FoodOrderItemDto;
import com.nhom9.movieBooking.dto.PaymentSuccessRequest;
import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
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

    private static final String STATUS_PENDING = "PENDING_PAYMENT";
    private static final String STATUS_PAID = "PAID";
    private static final String STATUS_CANCELLED = "CANCELLED";

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
            UserRepository userRepository
    ) {
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
        LocalDateTime now = LocalDateTime.now();

        if (userId == null) throw new RuntimeException("userId is required");
        if (showtimeId == null) throw new RuntimeException("showtimeId is required");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        List<Integer> seatIds = req.getSeatIds();
        if (seatIds == null || seatIds.isEmpty()) throw new RuntimeException("Seat list is empty");

        
        List<SeatHold> holds = seatholdRepository
                .findByUserUserIdAndShowtimeShowTimeIdAndExpireAtAfter(userId, showtimeId, now);

        if (holds.isEmpty()) throw new RuntimeException("No seats held or hold expired");

        Set<Integer> heldSeatIds = holds.stream()
                .map(h -> h.getSeat().getSeatId())
                .collect(Collectors.toSet());

        for (Integer seatId : seatIds) {
                if (!heldSeatIds.contains(seatId)) throw new RuntimeException("Seat not held: " + seatId);
        }

        
        List<String> lockedStatuses = List.of(STATUS_PAID, STATUS_PENDING);
        for (Integer seatId : seatIds) {
                boolean lockedSeat = bookingseatRepository
                        .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBookingIn(showtimeId, seatId, lockedStatuses);
                if (lockedSeat) throw new RuntimeException("Seat already locked: " + seatId);
        }

       
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setCreatedAt(now);
        booking.setStatusBooking(STATUS_PENDING);
        booking.setTotalTickets(seatIds.size());
        booking = bookingRepository.save(booking);

        
        BigDecimal seatTotal = BigDecimal.ZERO;
        BigDecimal seatPrice = showtime.getBasePrice() == null ? BigDecimal.ZERO : showtime.getBasePrice();

        for (Integer seatId : seatIds) {
                Seat seat = seatRepository.findById(seatId)
                        .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

                BookingSeat bs = new BookingSeat();
                bs.setBooking(booking);
                bs.setShowtime(showtime);
                bs.setSeat(seat);
                bs.setSeatPrice(seatPrice);

                bookingseatRepository.save(bs);
                seatTotal = seatTotal.add(seatPrice);
        }

       
        BigDecimal foodTotal = BigDecimal.ZERO;

        if (req.getFoodItems() != null) {
                for (FoodOrderItemDto item : req.getFoodItems()) {
                if (item == null || item.getFoodId() == null) continue;

                int qty = item.getQuantity();
                if (qty <= 0) continue;

                Food food = foodRepository.findById(item.getFoodId())
                        .orElseThrow(() -> new RuntimeException("Food not found: " + item.getFoodId()));

                BigDecimal unitPrice = food.getUnitPrice() == null ? BigDecimal.ZERO : food.getUnitPrice();
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

        
        List<SeatHold> toDelete = holds.stream()
                .filter(h -> seatIds.contains(h.getSeat().getSeatId()))
                .toList();
        seatholdRepository.deleteAll(toDelete);

        
        List<SeatDto> seatDtos = seatIds.stream()
                .map(id -> seatRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Seat not found: " + id)))
                .map(seat -> SeatMapper.toSeatDto(seat, SeatStatus.LOCKED))
                .toList();

       
        List<BookingResponseDto.FoodLineDto> foodLines = bookingfoodRepository
        .findByBookingBookingId(booking.getBookingId())
        .stream()
        .map(bf -> {
            BookingResponseDto.FoodLineDto line = new BookingResponseDto.FoodLineDto();
            line.setFoodId(bf.getFood().getFoodId());
            line.setFoodName(bf.getFood().getFoodName());
            line.setUnitPrice(bf.getUnitPriceAtOrder());
            line.setQuantity(bf.getQuantity());
            line.setLineTotal(bf.getLineTotal());
            return line;
        })
        .toList();


       
        BookingResponseDto res = new BookingResponseDto();
        res.setBookingId(booking.getBookingId());
        res.setUserId(userId);
        res.setShowtimeId(showtimeId);

        res.setSeats(seatDtos);
        res.setFoods(foodLines);

        res.setTicketUnitPrice(seatPrice);
        res.setTicketTotal(seatTotal);
        res.setFoodTotal(foodTotal);
        res.setTotalPay(totalPay);

        res.setStatus(booking.getStatusBooking());  
        res.setPaymentStatus("PENDING");

        return res;
        }

   
    @Override
    public CheckoutPreviewResponseDto previewCheckout(Integer showtimeId, CheckoutPreviewRequestDto req) {
        if (showtimeId == null) throw new RuntimeException("showtimeId is required");
        if (req.getUserId() == null) throw new RuntimeException("userId is required");
        if (req.getSeatIds() == null || req.getSeatIds().isEmpty()) throw new RuntimeException("seatIds is empty");

        Integer userId = req.getUserId();
        LocalDateTime now = LocalDateTime.now();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

     
        List<SeatHold> holds = seatholdRepository
                .findByUserUserIdAndShowtimeShowTimeIdAndExpireAtAfter(userId, showtimeId, now);

        if (holds.isEmpty()) throw new RuntimeException("No seats held or hold expired");

        Set<Integer> heldSeatIds = holds.stream()
                .map(h -> h.getSeat().getSeatId())
                .collect(Collectors.toSet());

        for (Integer seatId : req.getSeatIds()) {
            if (!heldSeatIds.contains(seatId)) throw new RuntimeException("Seat not held: " + seatId);
        }

      
        List<SeatDto> seatDtos = new ArrayList<>();
        BigDecimal seatTotal = BigDecimal.ZERO;
        BigDecimal seatPrice = showtime.getBasePrice() == null ? BigDecimal.ZERO : showtime.getBasePrice();

        for (Integer seatId : req.getSeatIds()) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            seatDtos.add(SeatMapper.toSeatDto(seat, SeatStatus.HOLD));
            seatTotal = seatTotal.add(seatPrice);
        }

     
        List<CheckoutPreviewResponseDto.FoodLineDto> foodLines = new ArrayList<>();
        BigDecimal foodTotal = BigDecimal.ZERO;

        if (req.getFoodItems() != null) {
            for (CheckoutPreviewRequestDto.FoodItemDto item : req.getFoodItems()) {
                if (item == null || item.getFoodId() == null) continue;
                int qty = item.getQuantity() == null ? 0 : item.getQuantity();
                if (qty <= 0) continue;

                Food food = foodRepository.findById(item.getFoodId())
                        .orElseThrow(() -> new RuntimeException("Food not found: " + item.getFoodId()));

                BigDecimal unitPrice = food.getUnitPrice() == null ? BigDecimal.ZERO : food.getUnitPrice();
                BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));

                CheckoutPreviewResponseDto.FoodLineDto line = new CheckoutPreviewResponseDto.FoodLineDto();
                line.setFoodId(food.getFoodId());
                line.setFoodName(food.getFoodName());
                line.setUnitPrice(unitPrice);
                line.setQuantity(qty);
                line.setLineTotal(lineTotal);

                foodLines.add(line);
                foodTotal = foodTotal.add(lineTotal);
            }
        }

        CheckoutPreviewResponseDto res = new CheckoutPreviewResponseDto();
        res.setUserId(user.getUserId());
        res.setShowtimeId(showtimeId);
        res.setSeats(seatDtos);
        res.setFoods(foodLines);
        res.setSeatTotal(seatTotal);
        res.setFoodTotal(foodTotal);
        res.setTotalPay(seatTotal.add(foodTotal));
        return res;
    }

   
    @Override
    @Transactional
    public BookingResponseDto paySuccess(Integer bookingId, PaymentSuccessRequest req) {

        if (bookingId == null) throw new RuntimeException("bookingId is required");

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!STATUS_PENDING.equals(booking.getStatusBooking())) {
            throw new RuntimeException("Booking not in PENDING_PAYMENT");
        }

        booking.setStatusBooking(STATUS_PAID);
        bookingRepository.save(booking);

     
        List<BookingSeat> bookingSeats = bookingseatRepository.findByBookingBookingId(bookingId);

        List<SeatDto> seatDtos = bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .map(seat -> SeatMapper.toSeatDto(seat, SeatStatus.SOLD))
                .toList();

        return new BookingResponseDto(
                booking.getBookingId(),
                "PAID",
                seatDtos,
                booking.getShowtime().getShowTimeId(),
                booking.getStatusBooking(),
                booking.getUser().getUserId()
        );
    }

    
    @Override
    @Transactional
    public BookingResponseDto cancel(Integer bookingId) {

        if (bookingId == null) throw new RuntimeException("bookingId is required");

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!STATUS_PENDING.equals(booking.getStatusBooking())) {
            throw new RuntimeException("Only PENDING_PAYMENT can be cancelled");
        }

        booking.setStatusBooking(STATUS_CANCELLED);
        bookingRepository.save(booking);

        
        bookingseatRepository.deleteByBookingBookingId(bookingId);
        bookingfoodRepository.deleteByBookingBookingId(bookingId);

        return new BookingResponseDto(
                booking.getBookingId(),
                STATUS_CANCELLED,
                List.of(),
                booking.getShowtime().getShowTimeId(),
                booking.getStatusBooking(),
                booking.getUser().getUserId()
        );
    }

        @Override
        @Transactional
        public BookingResponseDto markPaid(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!STATUS_PENDING.equals(booking.getStatusBooking())) {
                throw new RuntimeException("Booking not in PENDING_PAYMENT");
        }

        booking.setStatusBooking(STATUS_PAID);
        bookingRepository.save(booking);

      
        List<SeatDto> seatDtos = bookingseatRepository.findByBookingBookingId(bookingId).stream()
                .map(BookingSeat::getSeat)
                .map(seat -> SeatMapper.toSeatDto(seat, SeatStatus.SOLD))
                .toList();

        return new BookingResponseDto(
                booking.getBookingId(),
                "PAID",
                seatDtos,
                booking.getShowtime().getShowTimeId(),
                booking.getStatusBooking(),
                booking.getUser().getUserId()
        );
        }

        private List<BookingResponseDto.FoodLineDto> buildFoodLines(Integer bookingId) {
                List<BookingFood> bfs = bookingfoodRepository.findByBookingBookingId(bookingId);
                if (bfs == null || bfs.isEmpty()) return Collections.emptyList();

                List<BookingResponseDto.FoodLineDto> lines = new ArrayList<>();
                for (BookingFood bf : bfs) {
                        BookingResponseDto.FoodLineDto line = new BookingResponseDto.FoodLineDto();
                        line.setFoodId(bf.getFood().getFoodId());
                        line.setFoodName(bf.getFood().getFoodName());
                        line.setUnitPrice(bf.getUnitPriceAtOrder());
                        line.setQuantity(bf.getQuantity());
                        line.setLineTotal(bf.getLineTotal());

                        lines.add(line);
                }
                return lines;
        }

}
