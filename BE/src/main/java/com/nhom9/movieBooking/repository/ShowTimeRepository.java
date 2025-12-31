package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.ShowTime;


@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer>{
    List<ShowTime> findByFilmFilmIdOrderByRoomCinemaCineNameAscStartTimeAsc(Integer filmId);
    List<ShowTime> findByFilmFilmIdOrderByStartTimeAsc(Integer filmId);
    List<ShowTime> findByFilmFilmId(Integer filmId);
    List<ShowTime> findByRoomCinemaCinemaId(Integer cinemaId);



}
