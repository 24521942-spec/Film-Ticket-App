package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByCinema_CinemaId(Integer cinemaId);
}
