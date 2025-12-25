package com.nhom9.movieBooking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
import com.nhom9.movieBooking.mapper.ShowtimeMapper;
import com.nhom9.movieBooking.model.BookingSeat;
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
import com.nhom9.movieBooking.service.ShowtimeService;

import jakarta.transaction.Transactional;

@Service
public class ShowTimeServiceImpl implements ShowtimeService {

    private final SeatService seatService;

    private final ShowTimeRepository showtimeRepository;
    private final SeatholdRepository seatholdRepository;
    private final UserRepository userRepository;
    private final BookingseatRepository bookingseatRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public ShowTimeServiceImpl(
            ShowTimeRepository showtimeRepository,
            SeatholdRepository seatholdRepository,
            UserRepository userRepository,
            BookingseatRepository bookingseatRepository,
            SeatRepository seatRepository,
            SeatService seatService
    ) {
        this.showtimeRepository = showtimeRepository;
        this.seatholdRepository = seatholdRepository;
        this.userRepository = userRepository;
        this.bookingseatRepository = bookingseatRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
    }

    @Override
    public List<ShowtimeDto> getAllShowtimes() {
        List<ShowTime> showTimes = showtimeRepository.findAll();
        return showTimes.stream()
                .map(ShowtimeMapper::toShowtimeDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShowtimeDto getShowtimeById(Integer id) {
        ShowTime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public ShowtimeDto createShowtime(ShowtimeDto showtimeDto) {
        ShowTime showtime = new ShowTime();

        // ✅ FIX: set từ showtimeDto (code cũ set từ chính showtime => null)
        showtime.setBasePrice(showtimeDto.getBasePrice());
        showtime.setLanguageFilm(showtimeDto.getLanguageFilm());
        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setSubtitle(showtimeDto.getSubtitle());

        showtimeRepository.save(showtime);
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public ShowtimeDto updateShowtime(Integer id, ShowtimeDto showtimeDto) {
        ShowTime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // ✅ FIX: set từ showtimeDto
        showtime.setBasePrice(showtimeDto.getBasePrice());
        showtime.setLanguageFilm(showtimeDto.getLanguageFilm());
        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setSubtitle(showtimeDto.getSubtitle());

        showtimeRepository.save(showtime);
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public void deleteShowtime(Integer id) {
        showtimeRepository.deleteById(id);
    }

    @Override
    public Map<String, List<LocalDateTime>> getShowtimeByFilmGroupByCinema(Integer filmId) {
        List<ShowTime> showtimes = showtimeRepository
                .findByFilmFilmIdOrderByRoomCinemaCineNameAscStartTimeAsc(filmId);

        return showtimes.stream()
                .collect(Collectors.groupingBy(
                        st -> st.getRoom().getCinema().getCineName(),
                        LinkedHashMap::new,
                        Collectors.mapping(ShowTime::getStartTime, Collectors.toList())
                ));
    }

    @Override
    public List<ShowTime> getAllShowtimeByFilmId(Integer filmId) {
        return showtimeRepository.findByFilmFilmIdOrderByStartTimeAsc(filmId);
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

        // cleanup expired hold
        List<SeatHold> expired = seatholdRepository.findByExpireAtBefore(now);
        if (!expired.isEmpty()) seatholdRepository.deleteAll(expired);

        Integer roomId = showtime.getRoom().getRoomId();
        LocalDateTime expireAt = now.plusMinutes(holdMinutes);

        List<SeatDto> result = new ArrayList<>();

        for (Integer seatId : seatIds) {

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            // check seat thuộc đúng phòng của showtime
            if (seat.getRoom() != null && !seat.getRoom().getRoomId().equals(roomId)) {
                throw new RuntimeException("Seat " + seatId + " not in this showtime room");
            }

            List<String> lockedStatuses = List.of("PAID", "PENDING_PAYMENT");

            // LOCKED/PAID?
            boolean isLockedOrPaid = bookingseatRepository
                    .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBookingIn(
                            showtimeId, seatId, lockedStatuses
                    );
            if (isLockedOrPaid) throw new RuntimeException("Seat already locked/sold: " + seat.getSeatCode());

            // HOLD còn hạn?
            boolean isHold = seatholdRepository
                    .existsBySeatSeatIdAndShowtimeShowTimeIdAndExpireAtAfter(seatId, showtimeId, now);
            if (isHold) throw new RuntimeException("Seat already hold: " + seat.getSeatCode());

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

    @Override
    public List<SeatDto> getSeatsByShowtime(Integer showtimeId) {
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        LocalDateTime now = LocalDateTime.now();

        Integer roomId = showtime.getRoom().getRoomId();

        // ✅ seat trong phòng của showtime -> seatId lấy từ DB (UNIQUE)
        List<Seat> seats = seatRepository.findByRoomRoomId(roomId);

        // ✅ booking_seat của showtime (PAID hoặc PENDING_PAYMENT)
        List<BookingSeat> bsList = bookingseatRepository.findByShowtimeAndBookingStatusIn(
                showtimeId,
                List.of("PAID", "PENDING_PAYMENT")
        );

        // map seatId -> bookingStatus (PAID / PENDING_PAYMENT)
        Map<Integer, String> seatIdToBookingStatus = bsList.stream()
                .collect(Collectors.toMap(
                        bs -> bs.getSeat().getSeatId(),
                        bs -> bs.getBooking().getStatusBooking(),
                        (a, b) -> a
                ));

        // ✅ HOLD: lấy 1 lần tất cả hold còn hạn của showtime để tránh N+1 query
        List<SeatHold> holdList = seatholdRepository.findByShowtimeShowTimeIdAndExpireAtAfter(showtimeId, now);
        Set<Integer> holdingSeatIds = holdList.stream()
                .map(h -> h.getSeat().getSeatId())
                .collect(Collectors.toCollection(HashSet::new));

        return seats.stream()
                .map(seat -> {

                    SeatStatus status;

                    String bookingStatus = seatIdToBookingStatus.get(seat.getSeatId());

                    // 1) PAID => SOLD
                    if ("PAID".equals(bookingStatus)) {
                        status = SeatStatus.SOLD;
                    }
                    // 2) PENDING_PAYMENT => LOCKED
                    else if ("PENDING_PAYMENT".equals(bookingStatus)) {
                        status = SeatStatus.LOCKED;
                    }
                    // 3) HOLD (chưa hết hạn)
                    else if (holdingSeatIds.contains(seat.getSeatId())) {
                        status = SeatStatus.HOLD;
                    }
                    // 4) AVAILABLE
                    else {
                        status = SeatStatus.AVAILABLE;
                    }

                    // ✅ dùng seatId thật từ DB
                    SeatDto dto = new SeatDto();
                        dto.setSeatId(seat.getSeatId());          // ✅ 1..15 đúng theo DB
                        dto.setSeatCode(seat.getSeatCode());      // ✅ A1, B1, C1
                        dto.setRowLabel(seat.getRowLabel());      // ✅ A, B, C
                        dto.setColLabel(seat.getColLabel());      // ✅ 1..5
                        dto.setSeatType(seat.getSeatType());
                        dto.setStatus(status);

                    return dto;


                })
                // optional: sort cho FE render đẹp theo row/col
                .sorted((a, b) -> {
                    int r = a.getRowLabel().compareToIgnoreCase(b.getRowLabel());
                    if (r != 0) return r;
                    return Integer.compare(a.getColLabel(), b.getColLabel());
                })
                .toList();
    }
    @Override
    @Transactional
    public List<SeatDto> releaseHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds) {

        if (userId == null) throw new RuntimeException("userId is required");
        if (seatIds == null || seatIds.isEmpty()) throw new RuntimeException("seatIds is empty");

        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        LocalDateTime now = LocalDateTime.now();

        // dọn hold hết hạn trước
        List<SeatHold> expired = seatholdRepository.findByExpireAtBefore(now);
        if (!expired.isEmpty()) seatholdRepository.deleteAll(expired);

        // chỉ xoá những hold thuộc user + showtime + còn hạn
        seatholdRepository.deleteByUserUserIdAndShowtimeShowTimeIdAndSeatSeatIdInAndExpireAtAfter(
                userId, showtimeId, seatIds, now
        );

        // trả list ghế đã release => AVAILABLE (để FE cập nhật)
        List<SeatDto> result = new ArrayList<>();
        for (Integer seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            // optional: check seat thuộc đúng room showtime
            if (seat.getRoom() != null && showtime.getRoom() != null
                    && !seat.getRoom().getRoomId().equals(showtime.getRoom().getRoomId())) {
                throw new RuntimeException("Seat " + seatId + " not in this showtime room");
            }

            result.add(SeatMapper.toSeatDto(seat, SeatStatus.AVAILABLE));
        }

        return result;
    }

    @Override
    @Transactional
    public List<SeatDto> extendHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes) {

        if (userId == null) throw new RuntimeException("userId is required");
        if (seatIds == null || seatIds.isEmpty()) throw new RuntimeException("seatIds is empty");
        if (holdMinutes <= 0) holdMinutes = 5;

        showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        LocalDateTime now = LocalDateTime.now();

        // dọn hold hết hạn trước
        List<SeatHold> expired = seatholdRepository.findByExpireAtBefore(now);
        if (!expired.isEmpty()) seatholdRepository.deleteAll(expired);

        // lấy các hold còn hạn của user cho đúng seatIds
        List<SeatHold> holds = seatholdRepository
                .findByUserUserIdAndShowtimeShowTimeIdAndSeatSeatIdInAndExpireAtAfter(userId, showtimeId, seatIds, now);

        if (holds.isEmpty()) throw new RuntimeException("No seats held to extend (hold expired or not yours)");

        // bắt buộc đủ tất cả seatIds (tránh extend thiếu)
        if (holds.size() != seatIds.size()) {
            throw new RuntimeException("Some seats are not held by this user or expired");
        }

        LocalDateTime newExpireAt = now.plusMinutes(holdMinutes);

        for (SeatHold h : holds) {
            h.setExpireAt(newExpireAt);
            // optional: cập nhật lại holdAt để log
            h.setHoldAt(now);
        }
        seatholdRepository.saveAll(holds);

        // trả list ghế => HOLD (để FE cập nhật + reset timer)
        return holds.stream()
                .map(h -> SeatMapper.toSeatDto(h.getSeat(), SeatStatus.HOLD))
                .toList();
    }

}
