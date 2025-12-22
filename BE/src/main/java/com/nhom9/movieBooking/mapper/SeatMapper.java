package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.model.Seat;

public class SeatMapper {
    public static SeatDto toSeatDto(Seat seat, SeatStatus status) {
        return new SeatDto(seat.getColLabel(), seat.getRowLabel(), seat.getSeatCode(), seat.getSeatId(), seat.getSeatType(), status);
    }
}
