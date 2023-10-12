package com.dpap.bookingapp.booking.reservation;//package com.dpap.bookingapp.booking.reservation;
//
//import com.dpap.bookingapp.booking.common.TimeSlot;
//import com.dpap.bookingapp.booking.common.TimeSlotService;
//import com.dpap.bookingapp.booking.reservation.room.Room;
//import com.dpap.bookingapp.booking.reservation.room.dto.RoomId;
//import com.dpap.bookingapp.booking.reservation.room.dto.RoomDTO;
//import com.dpap.bookingapp.booking.reservation.room.RoomService;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//
//
//@Component
//public class ReservationService {
//
//    private final TimeSlotService timeSlotService;
//    private final ReservationRepository database;
//    private final RoomService roomService;
//
//    public ReservationService(TimeSlotService timeSlotService, ReservationRepository database, RoomService roomService) {
//        this.timeSlotService = timeSlotService;
//        this.database = database;
//        this.roomService = roomService;
//    }
//
//    public List<Room> findAvailableRooms(LocalDateTime from, LocalDateTime to) {
//        return roomService.findAll()
//                .stream()
//                .filter(room -> Boolean.TRUE.equals(checkRoomAvailability(room.getId(), from, to)))
//                .toList();
//    }
//
//    public RoomDTO findRoomAvailability(RoomId roomId, LocalDateTime from, LocalDateTime to) {
//        var room = roomService.findRoomById(roomId);
//        return new RoomDTO(
//                room,
//                findFreeSlotsIn(roomId, from, to)
//        );
//    }
//
//    public boolean checkRoomAvailability(RoomId id, LocalDateTime from, LocalDateTime to) {
//        var freePeriods = findFreeSlotsIn(id, from, to);
//        var allPeriods = TimeSlotService.allTimeSlotsBetween(from, to);
//        for (TimeSlot period : allPeriods) {
//            if (!freePeriods.contains(period)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void reserveRooms(List<AddReservationRequest> request) {
//        request.forEach(this::reserveRoom);
//    }
//
//    public void reserveRoom(AddReservationRequest request) {
//        var room = roomService.findRoomById(RoomId.fromLong(request.roomId()));
//        if (room.canBeReserved() && (!checkRoomAvailability(RoomId.fromLong(request.roomId()), request.start(), request.finish()))) {
//            throw new RuntimeException("Can not reserve room");
//        }
//        Reservation reservation = Reservation.createReservation(
//                new TimeSlot(request.start(), request.finish()),
//                request.roomId(),
//                request.at(),
//                request.userId(),
//                BigDecimal.valueOf(room.getPricePerNight()),
//                14
//        );
//        database.save(reservation);
//    }
//
//    public void confirmReservation(Long reservationId) {
//        var reservation = findById(reservationId);
//        reservation.confirm();
//        database.updateState(reservation.getId(), reservation.getState());
//    }
//
//    public void checkInReservation(Long reservationId) {
//        var reservation = findById(reservationId);
//        reservation.checkIn();
//        database.updateState(reservation.getId(), reservation.getState());
//    }
//
//    public void checkOutReservation(Long reservationId) {
//        var reservation = findById(reservationId);
//        reservation.checkOut();
//        database.updateState(reservation.getId(), reservation.getState());
//    }
//
//    public void cancelReservation(Long reservationId) {
//        var reservation = findById(reservationId);
//        reservation.cancel(LocalDateTime.now());
//        database.updateState(reservation.getId(), reservation.getState());
//    }
//
//    public void addCost(Long reservationId, FeeRequest feeRequest) {
//        var reservation = findById(reservationId);
//        reservation.addCost(feeRequest.value());
//    }
//
//
//    Reservation findById(Long id) {
//        return database.findById(id).getOrElseThrow(() -> new RuntimeException("Not found Reservation " + id));
//    }
//
//    private List<TimeSlot> findFreeSlotsIn(RoomId roomId, LocalDateTime from, LocalDateTime to) {
//        List<Reservation> reservations = database.getByRoomId(roomId, from, to);
//        var reservedPeriods = reservations
//                .stream()
//                .filter(reservation -> !reservation.getState().equals(ReservationState.CANCELED))
//                .map(reservation -> timeSlotService.allTimeSlotsBetween(reservation.getCheckIn(), reservation.getCheckOut()))
//                .flatMap(Collection::stream)
//                .toList();
//        return timeSlotService.freeSlots(from, to, reservedPeriods);
//    }
//
//    public List<Reservation> findAll() {
//        return database.findAll();
//    }
//}
