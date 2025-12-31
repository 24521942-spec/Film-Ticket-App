package com.nhom9.movieBooking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
import com.nhom9.movieBooking.model.Seat;
import com.nhom9.movieBooking.model.SeatHold;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.repository.SeatRepository;
import com.nhom9.movieBooking.repository.SeatholdRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.SeatService;

import jakarta.transaction.Transactional;

@Service
public class SeatServiceImpl implements SeatService {

    private static final String STATUS_PAID = "PAID";
    private static final String STATUS_PENDING = "PENDING_PAYMENT";

    private final SeatRepository seatRepository;
    private final ShowTimeRepository showtimeRepository;
    private final SeatholdRepository seatholdRepository;
    private final BookingseatRepository bookingseatRepository;
    private final UserRepository userRepository;

    public SeatServiceImpl( SeatRepository seatRepository, SeatholdRepository seatholdRepository, ShowTimeRepository showtimeRepository, BookingseatRepository bookingseatRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.seatholdRepository = seatholdRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingseatRepository = bookingseatRepository;
        this.userRepository = userRepository;
    }




    @Override
    public List<SeatDto> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        List<SeatDto> result = new ArrayList<>();

        for (Seat seat : seats) {
            result.add(new SeatDto(
                seat.getColLabel(),
                seat.getRowLabel(),
                seat.getSeatCode(),
                seat.getSeatId(),
                seat.getSeatType(),
                SeatStatus.AVAILABLE 
            ));
        }
        return result;
    }

    @Override
    public SeatDto getSeatById(Integer id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
        return new SeatDto(
            seat.getColLabel(),
            seat.getRowLabel(),
            seat.getSeatCode(),
            seat.getSeatId(),
            seat.getSeatType(),
            SeatStatus.AVAILABLE
        );

    }

    @Override
    public SeatDto createSeat(SeatDto seatDto) {
        Seat seat = new Seat();
        seat.setColLabel(seatDto.getColLabel());
        seat.setRowLabel(seatDto.getRowLabel());
        seat.setSeatCode(seatDto.getSeatCode());
        seat.setSeatType(seatDto.getSeatType());
        seatRepository.save(seat);

        
        return new SeatDto(
            seat.getColLabel(),
            seat.getRowLabel(),
            seat.getSeatCode(),
            seat.getSeatId(),
            seat.getSeatType(),
            SeatStatus.AVAILABLE
        );
    }

    @Override
    public SeatDto updateSeat(Integer id, SeatDto seatDto) {
        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setColLabel(seatDto.getColLabel());
        seat.setRowLabel(seatDto.getRowLabel());
        seat.setSeatCode(seatDto.getSeatCode());
        seat.setSeatType(seatDto.getSeatType());
        seatRepository.save(seat);

        return new SeatDto(
            seat.getColLabel(),
            seat.getRowLabel(),
            seat.getSeatCode(),
            seat.getSeatId(),
            seat.getSeatType(),
            SeatStatus.AVAILABLE
        );
    }


    @Override
    public void deleteSeat(Integer id) {
        seatRepository.deleteById(id);
    }

    @Override
    public List<SeatDto> getSeatByShowtime(Integer showtimeId) {
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow( () -> new RuntimeException("Showtime not found"));
        
        Integer roomId = showtime.getRoom().getRoomId();

        List<Seat> seats = seatRepository.findByRoomRoomId(roomId);

        List<String> locked = List.of("PAID", "PENDING_PAYMENT");

        Set<Integer> soldSeatIds = bookingseatRepository
            .findByShowtimeShowTimeIdAndBookingStatusBookingIn(showtimeId, locked)
            .stream()
            .map(bs -> bs.getSeat().getSeatId())
            .collect(Collectors.toSet());


        Set<Integer> holdSeatIds = seatholdRepository
                .findByShowtimeShowTimeIdAndExpireAtAfter(showtimeId, LocalDateTime.now())
                .stream()
                .map(bs -> bs.getSeat().getSeatId())
                .collect(Collectors.toSet());

        

        List<SeatDto> result = new ArrayList<>();

        for(Seat seat : seats) {
            SeatStatus status;

            if(soldSeatIds.contains(seat.getSeatId())) {
                status = SeatStatus.SOLD;
            }
            else if (holdSeatIds.contains(seat.getSeatId())) {
                status = SeatStatus.HOLD;
            } else {
                status = SeatStatus.AVAILABLE;
            }

            result.add(new SeatDto(
                seat.getColLabel(),
                seat.getRowLabel(),
                seat.getSeatCode(),
                seat.getSeatId(),
                seat.getSeatType(),
                status
                ));

        }
        return result;

    }




    @Override
    @Transactional
    public List<SeatDto> holdSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes) {

        if (seatIds == null || seatIds.isEmpty()) {
            throw new RuntimeException("seatIds is empty");
        }
        if (holdMinutes <= 0) holdMinutes = 5;

        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime now = LocalDateTime.now();

        // dọn hold hết hạn
        List<SeatHold> expired = seatholdRepository.findByExpireAtBefore(now);
        if (!expired.isEmpty()) {
            seatholdRepository.deleteAll(expired);
        }

        Integer roomId = showtime.getRoom().getRoomId();
        LocalDateTime expireAt = now.plusMinutes(holdMinutes);

        List<SeatDto> result = new ArrayList<>();
        List<String> lockedStatuses = List.of(STATUS_PAID, STATUS_PENDING);

        for (Integer seatId : seatIds) {

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            // seat thuộc đúng phòng
            if (seat.getRoom() != null && !seat.getRoom().getRoomId().equals(roomId)) {
                throw new RuntimeException("Seat " + seatId + " not in this showtime room");
            }

            // LOCKED/SOLD?
            boolean isLockedOrSold = bookingseatRepository
                    .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBookingIn(showtimeId, seatId, lockedStatuses);
            if (isLockedOrSold) {
                throw new RuntimeException("Seat already locked/sold: " + seat.getSeatCode());
            }

            // HOLD còn hạn? (nếu của mình thì gia hạn)
            var existingHoldOpt = seatholdRepository
                    .findFirstBySeatSeatIdAndShowtimeShowTimeIdAndExpireAtAfter(seatId, showtimeId, now);

            if (existingHoldOpt.isPresent()) {
                SeatHold existingHold = existingHoldOpt.get();

                if (!existingHold.getUser().getUserId().equals(userId)) {
                    throw new RuntimeException("Seat already hold: " + seat.getSeatCode());
                }

                // hold của chính user -> gia hạn
                existingHold.setExpireAt(expireAt);
                seatholdRepository.save(existingHold);

                result.add(SeatMapper.toSeatDto(seat, SeatStatus.HOLD));
                continue;
            }

            // tạo hold mới
            SeatHold sh = new SeatHold();
            sh.setSeat(seat);
            sh.setShowtime(showtime);
            sh.setUser(user);
            sh.setHoldAt(now);
            sh.setExpireAt(expireAt);
            sh.setHoldStatus("HOLD");

            seatholdRepository.save(sh);

            result.add(SeatMapper.toSeatDto(seat, SeatStatus.HOLD));
        }

        return result;
    }




    
}
