package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.RoomDto;
import com.nhom9.movieBooking.model.Room;

public class RoomMapper {
    public static RoomDto toRoomDto(Room room) {
        return new RoomDto(room.getCapacity(), room.getRoomId(), room.getRoomName(), room.getRoomType());
    }
}
