package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.RoomDto;

@Service
public interface  RoomService {

    List<RoomDto> getAllRooms();
    RoomDto getRoomById(Integer id);
    RoomDto createRoom(RoomDto roomDto);
    RoomDto updateRoom(Integer id, RoomDto roomDto);
    void deleteRoom(Integer id);
    List<RoomDto> getRoomsByCinemaDtos(Integer cinemaId);
    
}
