package com.nhom9.movieBooking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.mapper.SeatMapper;
import com.nhom9.movieBooking.mapper.ShowtimeMapper;
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
public class ShowTimeServiceImpl implements ShowtimeService{

    private final SeatService seatService;

    private final ShowTimeRepository showtimeRepository;
    private final SeatholdRepository seatholdRepository;
    private final UserRepository userRepository;
    private final BookingseatRepository bookingseatRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public ShowTimeServiceImpl(ShowTimeRepository showtimeRepository, SeatholdRepository seatholdRepository, UserRepository userRepository, BookingseatRepository bookingseatRepository, SeatRepository seatRepository, SeatService seatService) {
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
        ShowTime showtime = showtimeRepository.findById(id).orElseThrow(() -> new RuntimeException("Showtime not found"));
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public ShowtimeDto createShowtime(ShowtimeDto showtimeDto) {
        ShowTime showtime = new ShowTime();
        showtime.setBasePrice(showtime.getBasePrice());
        showtime.setLanguageFilm(showtime.getLanguageFilm());
        showtime.setStartTime(showtime.getStartTime());
        showtime.setSubtitle(showtime.getSubtitle());
        showtimeRepository.save(showtime);
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public ShowtimeDto updateShowtime(Integer id, ShowtimeDto showtimeDto) {
        ShowTime showtime =  showtimeRepository.findById(id).orElseThrow(() -> new RuntimeException("Showtime not found"));
        showtime.setBasePrice(showtime.getBasePrice());
        showtime.setLanguageFilm(showtime.getLanguageFilm());
        showtime.setStartTime(showtime.getStartTime());
        showtime.setSubtitle(showtime.getSubtitle());
        showtimeRepository.save(showtime);
        return ShowtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    public void deleteShowtime(Integer id) {
        showtimeRepository.deleteById(id);
    }

    @Override
    public Map<String, List<LocalDateTime>> getShowtimeByFilmGroupByCinema(Integer filmId) {
        List<ShowTime> showtimes = showtimeRepository.findByFilmFilmIdOrderByRoomCinemaCineNameAscStartTimeAsc(filmId);
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

            // SOLD?
            boolean isSold = bookingseatRepository
                    .existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBooking(showtimeId, seatId, "PAID");
            if (isSold) throw new RuntimeException("Seat already sold: " + seat.getSeatCode());

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
        return seatService.getSeatByShowtime(showtimeId);
    }

    





    
}
