package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.dto.TicketDto;
import com.nhom9.movieBooking.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Object>{
    List<Ticket> findByBookingBookingId(Integer bookingId);
    @Query("""
        select new com.nhom9.movieBooking.dto.TicketDto(
            t.id,
            t.qrCode,
            b.id,
            s.seatCode,
            1
        )
        from Ticket t
        join t.booking b
        join t.seat s
        join b.user u
        where u.id = :userId
        order by t.id desc
    """)
    List<TicketDto> findTicketDtosByUserId(@Param("userId") Integer userId);

   









}
