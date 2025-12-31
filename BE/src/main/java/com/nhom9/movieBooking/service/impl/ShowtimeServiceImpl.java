package com.nhom9.movieBooking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeCreateUpdateDto;
import com.nhom9.movieBooking.dto.ShowtimeDetailDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
import com.nhom9.movieBooking.model.BookingSeat;
import com.nhom9.movieBooking.model.Film;
import com.nhom9.movieBooking.model.Room;
import com.nhom9.movieBooking.model.Seat;
import com.nhom9.movieBooking.model.SeatHold;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.BookingseatRepository;
import com.nhom9.movieBooking.repository.FilmRepository;
import com.nhom9.movieBooking.repository.RoomRepository;
import com.nhom9.movieBooking.repository.SeatRepository;
import com.nhom9.movieBooking.repository.SeatholdRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.ShowtimeService;

import jakarta.transaction.Transactional;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowTimeRepository showtimeRepository;
    private final SeatholdRepository seatholdRepository;
    private final UserRepository userRepository;
    private final BookingseatRepository bookingseatRepository;
    private final SeatRepository seatRepository;
    private final FilmRepository filmRepository;
    private final RoomRepository roomRepository;

    public ShowtimeServiceImpl(
            ShowTimeRepository showtimeRepository,
            SeatholdRepository seatholdRepository,
            UserRepository userRepository,
            BookingseatRepository bookingseatRepository,
            SeatRepository seatRepository,
            FilmRepository filmRepository,
            RoomRepository roomRepository
    ) {
        this.showtimeRepository = showtimeRepository;
        this.seatholdRepository = seatholdRepository;
        this.userRepository = userRepository;
        this.bookingseatRepository = bookingseatRepository;
        this.seatRepository = seatRepository;
        this.filmRepository = filmRepository;
        this.roomRepository = roomRepository;
    }

    // ================= ADMIN =================

    @Override
    public List<ShowtimeDetailDto> getAllShowtimes() {
        return showtimeRepository.findAll()
                .stream()
                .map(this::toDetailDto)
                .toList();
    }

    @Override
    public ShowtimeDetailDto getShowtimeDetail(Integer showtimeId) {
        ShowTime showTime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        return toDetailDto(showTime);
    }

    @Override
    public ShowtimeDetailDto createShowtime(ShowtimeCreateUpdateDto dto) {
        Film film = filmRepository.findById(dto.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ShowTime showtime = new ShowTime();
        showtime.setFilm(film);
        showtime.setRoom(room);
        showtime.setStartTime(dto.getStartTime());
        showtime.setBasePrice(dto.getBasePrice());

        // nếu DTO có 2 field này thì giữ, không có thì bỏ 2 dòng dưới
        showtime.setLanguageFilm(dto.getLanguageFilm());
        showtime.setSubtitle(dto.getSubtitle());

        showtimeRepository.save(showtime);
        return toDetailDto(showtime);
    }

    @Override
    public ShowtimeDetailDto updateShowtime(Integer showtimeId, ShowtimeCreateUpdateDto dto) {
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        Film film = filmRepository.findById(dto.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        showtime.setFilm(film);
        showtime.setRoom(room);
        showtime.setStartTime(dto.getStartTime());
        showtime.setBasePrice(dto.getBasePrice());

        showtime.setLanguageFilm(dto.getLanguageFilm());
        showtime.setSubtitle(dto.getSubtitle());

        showtimeRepository.save(showtime);
        return toDetailDto(showtime);
    }

    @Override
    public void deleteShowtime(Integer showtimeId) {
        showtimeRepository.deleteById(showtimeId);
    }

    // ================= PUBLIC =================

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
    public List<ShowtimeDetailDto> getShowtimesByFilm(Integer filmId) {
        return showtimeRepository.findByFilmFilmId(filmId)
                .stream()
                .map(this::toDetailDto)
                .toList();
    }

    @Override
    public List<ShowtimeDetailDto> getShowtimesByCinema(Integer cinemaId) {
        // bạn phải có method tương ứng trong ShowTimeRepository
        return showtimeRepository.findByRoomCinemaCinemaId(cinemaId)
                .stream()
                .map(this::toDetailDto)
                .toList();
    }

    // ================= SEAT =================

    @Override
    public List<SeatDto> getSeatsByShowtime(Integer showtimeId) {
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        LocalDateTime now = LocalDateTime.now();
        Integer roomId = showtime.getRoom().getRoomId();

        List<Seat> seats = seatRepository.findByRoomRoomId(roomId);

        List<BookingSeat> bsList = bookingseatRepository.findByShowtimeAndBookingStatusIn(
                showtimeId, List.of("PAID", "PENDING_PAYMENT")
        );

        Map<Integer, String> seatIdToBookingStatus = bsList.stream()
                .collect(Collectors.toMap(
                        bs -> bs.getSeat().getSeatId(),
                        bs -> bs.getBooking().getStatusBooking(),
                        (a, b) -> a
                ));

        List<SeatHold> holdList =
                seatholdRepository.findByShowtimeShowTimeIdAndExpireAtAfter(showtimeId, now);

        Set<Integer> holdingSeatIds = holdList.stream()
                .map(h -> h.getSeat().getSeatId())
                .collect(Collectors.toCollection(HashSet::new));

        return seats.stream()
                .map(seat -> {
                    SeatStatus status;
                    String bookingStatus = seatIdToBookingStatus.get(seat.getSeatId());

                    if ("PAID".equals(bookingStatus)) status = SeatStatus.SOLD;
                    else if ("PENDING_PAYMENT".equals(bookingStatus)) status = SeatStatus.LOCKED;
                    else if (holdingSeatIds.contains(seat.getSeatId())) status = SeatStatus.HOLD;
                    else status = SeatStatus.AVAILABLE;

                    return SeatMapper.toSeatDto(seat, status);
                })
                .sorted((a, b) -> {
                    int r = a.getRowLabel().compareToIgnoreCase(b.getRowLabel());
                    if (r != 0) return r;
                    return Integer.compare(a.getColLabel(), b.getColLabel());
                })
                .toList();
    }

    @Override
    @Transactional
    public List<SeatDto> holdSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes) {
        if (seatIds == null || seatIds.isEmpty()) throw new RuntimeException("seatIds is empty");
        if (holdMinutes <= 0) holdMinutes = 5;

        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = now.plusMinutes(holdMinutes);

        seatholdRepository.deleteAll(seatholdRepository.findByExpireAtBefore(now));

        List<SeatDto> result = new ArrayList<>();

        for (Integer seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            boolean locked = bookingseatRepository
                    .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBookingIn(
                            showtimeId, seatId, List.of("PAID", "PENDING_PAYMENT"));

            if (locked) throw new RuntimeException("Seat locked/sold");

            boolean holding = seatholdRepository
                    .existsBySeatSeatIdAndShowtimeShowTimeIdAndExpireAtAfter(seatId, showtimeId, now);

            if (holding) throw new RuntimeException("Seat already hold");

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
    @Transactional
    public List<SeatDto> releaseHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds) {
        LocalDateTime now = LocalDateTime.now();
        seatholdRepository.deleteAll(seatholdRepository.findByExpireAtBefore(now));

        seatholdRepository.deleteByUserUserIdAndShowtimeShowTimeIdAndSeatSeatIdInAndExpireAtAfter(
                userId, showtimeId, seatIds, now);

        return seatIds.stream()
                .map(id -> SeatMapper.toSeatDto(seatRepository.findById(id).orElseThrow(), SeatStatus.AVAILABLE))
                .toList();
    }

    @Override
    @Transactional
    public List<SeatDto> extendHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes) {
        if (holdMinutes <= 0) holdMinutes = 5;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newExpireAt = now.plusMinutes(holdMinutes);

        List<SeatHold> holds = seatholdRepository
                .findByUserUserIdAndShowtimeShowTimeIdAndSeatSeatIdInAndExpireAtAfter(
                        userId, showtimeId, seatIds, now);

        if (holds.size() != seatIds.size()) throw new RuntimeException("Some seats not held by this user");

        for (SeatHold h : holds) {
            h.setExpireAt(newExpireAt);
            h.setHoldAt(now);
        }

        seatholdRepository.saveAll(holds);

        return holds.stream()
                .map(h -> SeatMapper.toSeatDto(h.getSeat(), SeatStatus.HOLD))
                .toList();
    }

    // ================= MAPPER =================

    private ShowtimeDetailDto toDetailDto(ShowTime st) {
        ShowtimeDetailDto dto = new ShowtimeDetailDto();
        dto.setShowtimeId(st.getShowTimeId());
        dto.setStartTime(st.getStartTime() != null ? st.getStartTime().toString() : null);
        dto.setBasePrice(st.getBasePrice());

        if (st.getFilm() != null) {
            dto.setFilmId(st.getFilm().getFilmId());
            dto.setFilmTitle(st.getFilm().getTitle());
        }

        if (st.getRoom() != null) {
            dto.setRoomId(st.getRoom().getRoomId());
            dto.setRoomName(st.getRoom().getRoomName());

            if (st.getRoom().getCinema() != null) {
                dto.setCinemaId(st.getRoom().getCinema().getCinemaId()); // sửa theo field thật
                dto.setCinemaName(st.getRoom().getCinema().getCineName());
            }
        }
        return dto;
    }
}
