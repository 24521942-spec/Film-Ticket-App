package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.enums.SeatStatus;
import com.nhom9.movieBooking.model.Seat;

public class SeatMapper {
    public static SeatDto toSeatDto(Seat seat, SeatStatus status) {
        SeatDto dto = new SeatDto();

        dto.setSeatId(seat.getSeatId());      
        dto.setRowLabel(seat.getRowLabel());
        dto.setColLabel(seat.getColLabel());
        dto.setSeatCode(seat.getRowLabel() + seat.getColLabel());
        dto.setSeatType(seat.getSeatType());
        dto.setStatus(status);

        return dto;
}

}
