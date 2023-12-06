package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.auth.AuthenticationService;
import com.dpap.bookingapp.booking.reservation.dto.AddReservationRequest;
import com.dpap.bookingapp.booking.reservation.dto.FeeRequest;
import com.dpap.bookingapp.booking.reservation.dto.ReservationDto;
import com.dpap.bookingapp.booking.reservation.dto.SearchReservationFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@Tag(name = "Reservations")
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;
    private final ReservationHostService reservationHostService;
    private final AuthenticationService authenticationService;

    public ReservationController(ReservationService service,
                                 ReservationHostService reservationHostService,
                                 AuthenticationService authenticationService) {
        this.service = service;
        this.reservationHostService = reservationHostService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody AddReservationRequest request) {
        service.reserveRoom(request, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(201).build();
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> fetchReservations(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "roomId", required = false) Long roomId,
            @RequestParam(value = "placeId", required = false) Long placeId,
            @RequestParam(value = "state", required = false) ReservationState state,
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to) {
        var filter = new SearchReservationFilter(userId, placeId, roomId, state, from, to);
        return ResponseEntity.ok(service.findReservations(filter));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationDto>> fetchMyReservations(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "placeId", required = false) Long placeId,
            @RequestParam(value = "state", required = false) ReservationState state,
            @RequestParam(value = "to", required = false) LocalDateTime to) {
        var filter = new SearchReservationFilter(authenticationService.getLoggedUser().id(), placeId, null, state, from, to);
        return ResponseEntity.ok(service.findReservations(filter).stream()
                .map(r -> new ReservationDto(
                        r.getId(),
                        r.getRoomId(), r.getPlaceId(),
                        r.getCheckIn().toString(), r.getCheckOut().toString(),
                        r.getAt().toString(), r.getUserId(), r.getState(), r.getValue(), 14)
                ).toList());
    }


    @PutMapping("/{id}/cancellation")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        service.cancelReservation(id, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(204).build();
    }


    @PutMapping("/{id}/check-in")
    public ResponseEntity<?> checkIn(@PathVariable Long id) {
        service.checkInReservation(authenticationService.getLoggedUser().id(), id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}/check-out")
    public ResponseEntity<?> checkOut(@PathVariable Long id) {
        service.checkOutReservation(authenticationService.getLoggedUser().id(), id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}/fee")
    public ResponseEntity<?> addFee(@PathVariable Long id, @RequestBody FeeRequest feeRequest) {
        service.addCost(id, authenticationService.getLoggedUser().id(), feeRequest);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/offers")
    public ResponseEntity<List<ReservationDto>> fetchMyOffers() {
        return ResponseEntity.ok(reservationHostService.findAllOffers(authenticationService.getLoggedUser().id())
                .stream()
                .map(r -> new ReservationDto(
                        r.getId(),
                        r.getRoomId(), r.getPlaceId(),
                        r.getCheckIn().toString(), r.getCheckOut().toString(),
                        r.getAt().toString(), r.getUserId(), r.getState(), r.getValue(), 14)
                ).toList());
    }

    @GetMapping("/confirmation")
    public ResponseEntity<List<ReservationDto>> fetchReservationsToConfirm() {
        return ResponseEntity.ok(reservationHostService.findAllOffers(authenticationService.getLoggedUser().id())
                .stream()
                .map(r -> new ReservationDto(
                        r.getId(),
                        r.getRoomId(), r.getPlaceId(),
                        r.getCheckIn().toString(), r.getCheckOut().toString(),
                        r.getAt().toString(), r.getUserId(), r.getState(), r.getValue(), 14)
                ).toList());
    }

    @PutMapping("/{id}/confirmation")
    public ResponseEntity<?> confirm(@PathVariable Long id) {
        reservationHostService.confirmReservation(authenticationService.getLoggedUser().id(), id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/offers/{id}/cancellation")
    public ResponseEntity<?> cancelOffer(@PathVariable Long id) {
        reservationHostService.cancelOffer(id, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(204).build();
    }

}
