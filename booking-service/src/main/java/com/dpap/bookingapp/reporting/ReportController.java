package com.dpap.bookingapp.reporting;

import com.dpap.bookingapp.booking.reservation.ReservationService;
import com.dpap.bookingapp.booking.reservation.dto.SearchReservationFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reports")
@Tag(name="Reports")

public class ReportController {

    private final ReservationService reservationService;
    private final ReportService reportService;

    public ReportController(ReservationService reservationService, ReportService reportService) {
        this.reservationService = reservationService;
        this.reportService = reportService;
    }

    @PostMapping
    public void createReport(@RequestParam Long placeId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        var reservations = reservationService.findReservations(SearchReservationFilter.Builder.newBuilder().placeId(placeId).from(from).to(to).build());
        reportService.createReport(reservations, createFileName(placeId, from, to));

    }

    private String createFileName(Long placeId, LocalDateTime from, LocalDateTime to) {
        return placeId.toString() + "_" + from.toString() + "_" + to.toString();
    }
}
