package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.TicketDto;

@Service
public interface  TicketService {
    List<TicketDto> createTicketsAfterPaid(Integer bookingId);
    List<TicketDto> getTicketsByBooking(Integer bookingId);
    List<TicketDto> getTicketsByUser(Integer userId);

    
}
