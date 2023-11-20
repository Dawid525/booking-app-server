package com.dpap.bookingapp.booking.reservation;


import com.dpap.bookingapp.availability.service.AvailabilityService;
import com.dpap.bookingapp.booking.place.PlaceService;
import com.dpap.bookingapp.booking.place.room.RoomService;
import com.dpap.bookingapp.booking.place.room.dto.RoomId;
import com.dpap.bookingapp.booking.reservation.dto.AddReservationRequest;
import com.dpap.bookingapp.booking.reservation.dto.FeeRequest;
import com.dpap.bookingapp.booking.reservation.dto.SearchReservationFilter;
import com.dpap.bookingapp.notification.NotificationService;
import com.dpap.bookingapp.timeslot.TimeSlot;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationService {

    private final AvailabilityService availabilityService;
    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    public ReservationService(AvailabilityService availabilityService, ReservationRepository reservationRepository, RoomService roomService, NotificationService notificationService, PlaceService placeService) {
        this.availabilityService = availabilityService;
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
    }


    @Transactional
    public void reserveRoom(AddReservationRequest request, Long userId) {
        var room = roomService.findRoomById(RoomId.fromLong(request.roomId()))
                .orElseThrow(() -> new RuntimeException("Not found room with id: " + request.roomId()));
        if (room.canBeReserved() &&
                (!checkRoomAvailability(RoomId.fromLong(request.roomId()), new TimeSlot(request.start(), request.finish())))) {
            throw new RuntimeException("Can not reserve room");
        }
        var now = LocalDateTime.now();
        Reservation reservation = Reservation.createReservation(
                new TimeSlot(request.start(), request.finish()),
                request.roomId(),
                now,
                userId,
                request.placeId(),
                BigDecimal.valueOf(room.getPricePerNight()),
                14
        );
        availabilityService.reserveObject(request.roomId(), new TimeSlot(request.start(), request.finish()), now);
        reservationRepository.save(reservation);
    }


    public void checkInReservation(Long userId, Long reservationId) {
        var reservation = findByIdAndUserId(reservationId, userId);
        reservation.checkIn();
        reservationRepository.updateState(reservation.getId(), reservation.getState());
    }

    public void checkOutReservation(Long reservationId, Long userId) {
        var reservation = findByIdAndUserId(reservationId, userId);
        ;
        reservation.checkOut();
        reservationRepository.updateState(reservation.getId(), reservation.getState());
    }

    public void cancelReservation(Long reservationId, Long userId) {
        var reservation = findByIdAndUserId(reservationId, userId);
        reservation.cancel(LocalDateTime.now());
        reservationRepository.updateState(reservation.getId(), reservation.getState());
    }

    public void addCost(Long reservationId, Long userId, FeeRequest feeRequest) {
        var reservation = findByIdAndUserId(reservationId, userId);
        reservation.addCost(feeRequest.value());
    }


    Reservation findByIdAndUserId(Long reservationId, Long userId) {
        return reservationRepository.findByIdAndUserId(reservationId, userId).getOrElseThrow(() -> new RuntimeException("Not found Reservation " + reservationId));
    }

    Reservation findById(Long id) {
        return reservationRepository.findById(id).getOrElseThrow(() -> new RuntimeException("Not found Reservation " + id));
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    private List<TimeSlot> findFreeSlotsIn(RoomId roomId, LocalDateTime from, LocalDateTime to, Integer duration) {
        List<TimeSlot> splittedReservedTimeSlots = availabilityService.findAllReservedSlotsInPeriod(roomId.getId())
                .stream()
                .map((timeSlot -> timeSlot.allTimeSlotsWithDurationBetweenEach(duration)))
                .flatMap(List::stream)
                .toList();
        List<TimeSlot> allPeriods = new TimeSlot(from, to).allTimeSlotsWithDurationBetweenEach(duration);

        return allPeriods.stream().filter(period -> !splittedReservedTimeSlots.contains(period)).toList();
    }
//
//    public List<Room> findAvailableRooms(LocalDateTime from, LocalDateTime to) {
//        return roomService.findAllByPlaceId()
//                .stream()
//                .filter(room -> Boolean.TRUE.equals(checkRoomAvailability(room., new TimeSlot(from, to))))
//                .toList(); //TODO DD
//    }
//
//    public RoomDTO findRoomAvailability(RoomId roomId, LocalDateTime from, LocalDateTime to) {
//        var room = roomService.findRoomById(roomId);
//        return new RoomDTO(
//                room,
//                findFreeSlotsIn(roomId, from, to, 22)
//        ); //TODO XX
//    }fca

    public boolean checkRoomAvailability(RoomId id, TimeSlot timeSlot) {
        return availabilityService.isObjectAvailable(id.getId(), timeSlot);
    }

    public List<Reservation> findReservations(SearchReservationFilter filter) {
        return reservationRepository.findAllByFilters(filter);
    }

    public List<Reservation> findAllToAccept(Long hostId) {
        return reservationRepository.findAllToAccept(hostId);
    }
}
