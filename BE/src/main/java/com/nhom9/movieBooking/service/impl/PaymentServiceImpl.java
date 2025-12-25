package com.nhom9.movieBooking.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nhom9.movieBooking.dto.BookingResponseDto;
import com.nhom9.movieBooking.dto.PaymentQrResponse;
import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
import com.nhom9.movieBooking.model.Booking;
import com.nhom9.movieBooking.model.BookingFood;
import com.nhom9.movieBooking.model.BookingSeat;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.repository.BookingFoodRepository;
import com.nhom9.movieBooking.repository.BookingRepository;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.service.PaymentService;


@Service
public class PaymentServiceImpl implements PaymentService{

    private final BookingRepository bookingRepository;
    private final BookingseatRepository bookingseatRepository;
    private final BookingFoodRepository bookingfoodRepository;
    private static final String STATUS_PENDING = "PENDING_PAYMENT";

    public PaymentServiceImpl(BookingRepository bookingRepository, BookingFoodRepository bookingfoodRepository, BookingseatRepository bookingseatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingfoodRepository = bookingfoodRepository;
        this.bookingseatRepository = bookingseatRepository;
    }


    
    
    

    @Transactional(readOnly = true)
    public PaymentQrResponse generatePaymentQr(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        
        if (!STATUS_PENDING.equals(booking.getStatusBooking())) {
            throw new RuntimeException("Booking is not pending payment");
        }

        BigDecimal amount = booking.getTotalPay() == null ? BigDecimal.ZERO : booking.getTotalPay();

        
        String orderCode = "BK" + bookingId + "-" + System.currentTimeMillis();
        String qrContent = String.format(
                "MOVIE_BOOKING|bookingId=%d|amount=%s|order=%s|ts=%s",
                bookingId,
                amount.toPlainString(),
                orderCode,
                LocalDateTime.now()
        );

        String base64Png = toQrBase64Png(qrContent, 320, 320);

        return new PaymentQrResponse(
                bookingId,
                amount,
                qrContent,
                "data:image/png;base64," + base64Png
        );
    }

    private String toQrBase64Png(String content, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Generate QR failed: " + e.getMessage(), e);
        }
    }

    

    @Transactional
    public BookingResponseDto paymentSuccess(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

       
        if ("PAID".equals(booking.getStatusBooking())) {
            return buildBookingResponse(booking, "PAID");
        }

        
        if (!"PENDING_PAYMENT".equals(booking.getStatusBooking())) {
            throw new RuntimeException("Booking is not pending payment");
        }

        booking.setStatusBooking("PAID");
        bookingRepository.save(booking);

        return buildBookingResponse(booking, "PAID");
    }

    @Transactional
    public BookingResponseDto paymentFail(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if ("PAID".equals(booking.getStatusBooking())) {
            throw new RuntimeException("Booking already PAID, cannot fail");
        }

        if ("CANCELLED".equals(booking.getStatusBooking())) {
            return buildBookingResponse(booking, "CANCELLED");
        }

       
        BookingResponseDto res = buildBookingResponse(booking, "CANCELLED");

        
        booking.setStatusBooking("CANCELLED");
        bookingRepository.save(booking);

        
        bookingseatRepository.deleteByBookingBookingId(bookingId);

        
        bookingfoodRepository.deleteByBookingBookingId(bookingId);

        return res;
    }


    private BookingResponseDto buildBookingResponse(Booking booking, String paymentStatus) {
        Integer bookingId = booking.getBookingId();
        Integer userId = booking.getUser().getUserId();
        Integer showtimeId = booking.getShowtime().getShowTimeId();

        
        List<BookingSeat> bss = bookingseatRepository.findByBookingBookingId(bookingId);
        List<SeatDto> seatDtos = bss.stream()
                .map(bs -> SeatMapper.toSeatDto(
                        bs.getSeat(),
                        "PAID".equals(booking.getStatusBooking()) ? SeatStatus.SOLD : SeatStatus.LOCKED
                ))
                .toList();

        BigDecimal seatTotal = bss.stream()
                .map(bs -> bs.getSeatPrice() == null ? BigDecimal.ZERO : bs.getSeatPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        
        List<BookingFood> bfs = bookingfoodRepository.findByBookingBookingId(bookingId);

        List<BookingResponseDto.FoodLineDto> foodLines = bfs.stream()
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

        BigDecimal foodTotal = bfs.stream()
                .map(bf -> bf.getLineTotal() == null ? BigDecimal.ZERO : bf.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPay = seatTotal.add(foodTotal);

      
        BookingResponseDto res = new BookingResponseDto();

       
        res.setBookingId(bookingId);

        res.setUserId(userId);
        res.setShowtimeId(showtimeId);

        
        ShowTime st = booking.getShowtime();

        if (st != null) {
            res.setTicketUnitPrice(st.getBasePrice());
            if (st.getStartTime() != null) {
                res.setShowTime(st.getStartTime().toString());
            }

            if (st.getRoom() != null) {
                res.setRoomName(st.getRoom().getRoomName());

                if (st.getRoom().getCinema() != null) {
                    res.setCinemaName(st.getRoom().getCinema().getCineName());
                }
            }
        }


        res.setSeats(seatDtos);
        res.setFoods(foodLines);

        
        res.setTicketTotal(seatTotal);
        res.setFoodTotal(foodTotal);
        res.setTotalPay(totalPay);

        res.setStatus(paymentStatus);        
        res.setPaymentStatus(paymentStatus);  
                
        if ("PAID".equals(booking.getStatusBooking())) {

            String seatCodes = seatDtos.stream()
                    .map(SeatDto::getSeatCode)
                    .sorted()
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            String gateQrContent = String.format(
                    "MOVIE_GATE|bookingId=%d|showtimeId=%d|seats=%s",
                    bookingId,
                    showtimeId,
                    seatCodes
            );

            res.setGateQrContent(gateQrContent);
            res.setGateQrBase64Png("data:image/png;base64," + toQrBase64Png(gateQrContent, 320, 320));
        }

        return res;
    }



}
