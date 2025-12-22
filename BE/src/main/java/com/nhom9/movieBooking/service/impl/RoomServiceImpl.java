package com.nhom9.movieBooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.RoomDto;
import com.nhom9.movieBooking.mapper.RoomMapper;
import com.nhom9.movieBooking.model.Room;
import com.nhom9.movieBooking.repository.RoomRepository;
import com.nhom9.movieBooking.service.RoomService;

@Service
public class  RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(RoomMapper::toRoomDto)
                .collect(Collectors.toList());
        }

    @Override
    public RoomDto getRoomById(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setCapacity(room.getCapacity());
        room.setRoomName(room.getRoomName());
        room.setRoomType(room.getRoomType());
        roomRepository.save(room);
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public RoomDto updateRoom(Integer id, RoomDto roomDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setCapacity(roomDto.getCapacity());
        room.setRoomName(roomDto.getRoomName());
        room.setRoomType(roomDto.getRoomType());
        roomRepository.save(room);
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDto> getRoomsByCinemaDtos(Integer cinemaId) {
        List<Room> rooms = roomRepository.findByCinema_CinemaId(cinemaId);
                return rooms.stream()
                .map(RoomMapper::toRoomDto)
                .collect(Collectors.toList());
        }
}

    

