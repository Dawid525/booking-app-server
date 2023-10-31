package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.auth.AuthenticationService;
import com.dpap.bookingapp.booking.reservation.dto.AddReservationRequest;
import com.dpap.bookingapp.booking.reservation.dto.FeeRequest;
import com.dpap.bookingapp.booking.reservation.dto.SearchReservationFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Tag(name="Reservations")
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;
    private final ReservationOwnerService reservationOwnerService;
    private final AuthenticationService authenticationService;

    public ReservationController(ReservationService service,
                                 ReservationOwnerService reservationOwnerService,
                                 AuthenticationService authenticationService) {
        this.service = service;
        this.reservationOwnerService = reservationOwnerService;
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
    public ResponseEntity<List<Reservation>> fetchMyReservations(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "placeId", required = false) Long placeId,
            @RequestParam(value = "state", required = false) ReservationState state,
            @RequestParam(value = "to", required = false) LocalDateTime to) {
        var filter = new SearchReservationFilter(authenticationService.getLoggedUser().id(), placeId, null, state, from, to);
        return ResponseEntity.ok(service.findReservations(filter));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<List<Reservation>> fetchReservationsToConfirm() {
        return ResponseEntity.ok(service.findAllToAccept(authenticationService.getLoggedUser().id()));
    }

    @PutMapping("/{id}/confirmation")
    public ResponseEntity<?> confirm(@PathVariable Long id) {
        reservationOwnerService.confirmReservation(authenticationService.getLoggedUser().id(), id);
        return ResponseEntity.status(204).build();
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

//TODO xd
//    @GetMapping("/rooms/{roomId}/availability")
//    public ResponseEntity<RoomDTO> getRoomAvailability(
//            @RequestParam LocalDateTime from,
//            @RequestParam LocalDateTime to,
//            @PathVariable Long roomId) {
//        return ResponseEntity.ok(service.findRoomAvailability(RoomId.fromLong(roomId), from, to));
//    }
//
//
//    @GetMapping("/rooms/free")
//    public ResponseEntity<List<Room>> getAllAvailableRooms(
//            @RequestParam LocalDateTime from,
//            @RequestParam LocalDateTime to) {
//        return ResponseEntity.ok(service.findAvailableRooms(from, to));
//    }
}
