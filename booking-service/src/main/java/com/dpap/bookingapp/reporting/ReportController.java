package com.dpap.bookingapp.reporting;

import com.dpap.bookingapp.booking.reservation.ReservationService;
import com.dpap.bookingapp.booking.reservation.dto.SearchReservationFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports")
@CrossOrigin("http://localhost:4200")

public class ReportController {

    private final ReservationService reservationService;
    private final ReportService reportService;

    public ReportController(ReservationService reservationService, ReportService reportService) {
        this.reservationService = reservationService;
        this.reportService = reportService;
    }

//    @PostMapping
//    public void createReport(@RequestParam Long placeId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
//        var reservations = reservationService.findReservations(SearchReservationFilter.Builder.newBuilder().placeId(placeId).from(from).to(to).build());
//        reportService.createReport(reservations, createFileName(placeId, from, to));
//    }

    @PostMapping
    public ResponseEntity<Resource> createReports(@RequestBody GenerateReportRequest generateReportRequest) throws MalformedURLException {
//    public ResponseEntity<Resource> createReports(@RequestParam Long placeId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) throws MalformedURLException {
        var reservations = reservationService
                .findReservations(SearchReservationFilter.Builder.newBuilder()
                        .placeId(generateReportRequest.getPlaceId())
                        .from(generateReportRequest.getFrom()).to(generateReportRequest.getTo()).build());
        var fileName = createFileName(generateReportRequest.getPlaceId(), generateReportRequest.getFrom(), generateReportRequest.getTo());
        reportService.createReport(reservations, fileName);
        Path path = Paths.get(fileName);
        Resource resource = new org.springframework.core.io.FileUrlResource(path.toString() + ".json");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    private String createFileName(Long placeId, LocalDateTime from, LocalDateTime to) {
        return "place_" + placeId.toString() + "_reservations";
//        return placeId.toString() + "_" + from.toString() + "_" + to.toString();
    }
}
