package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nhom9.movieBooking.dto.TicketDto;
import com.nhom9.movieBooking.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // ✅ tạo ticket sau khi thanh toán (fake cũng được)
    @PostMapping("/booking/{bookingId}/create")
    public ResponseEntity<List<TicketDto>> createTickets(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(ticketService.createTicketsAfterPaid(bookingId));
    }

    // ✅ lấy ticket theo bookingId
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<TicketDto>> getTickets(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(ticketService.getTicketsByBooking(bookingId));
    }

    // ✅ lịch sử vé theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

}
