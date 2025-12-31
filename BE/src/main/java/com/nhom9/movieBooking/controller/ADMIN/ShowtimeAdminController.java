package com.nhom9.movieBooking.controller.ADMIN;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.ShowtimeCreateUpdateDto;
import com.nhom9.movieBooking.dto.ShowtimeDetailDto;
import com.nhom9.movieBooking.service.ShowtimeService;

@RestController
@RequestMapping("/api/admin/showtimes")
public class ShowtimeAdminController {
    private final ShowtimeService showtimeService;

    public ShowtimeAdminController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    // 1) List all showtimes (admin)
    @GetMapping
    public List<ShowtimeDetailDto> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    // 2) Get by id (admin)
    @GetMapping("/{showtimeId}")
    public ShowtimeDetailDto getShowtime(@PathVariable Integer showtimeId) {
        return showtimeService.getShowtimeDetail(showtimeId);
    }

    // 3) Create
    @PostMapping
    public ShowtimeDetailDto create(@RequestBody ShowtimeCreateUpdateDto dto) {
        return showtimeService.createShowtime(dto);
    }

    // 4) Update
    @PutMapping("/{showtimeId}")
    public ShowtimeDetailDto update(@PathVariable Integer showtimeId,
                                    @RequestBody ShowtimeCreateUpdateDto dto) {
        return showtimeService.updateShowtime(showtimeId, dto);
    }

    // 5) Delete
    @DeleteMapping("/{showtimeId}")
    public void delete(@PathVariable Integer showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
    }
}
