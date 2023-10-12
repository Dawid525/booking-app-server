//package com.dpap.bookingapp.booking.room.web;
//
//import com.dpap.bookingapp.auth.AuthenticationService;
//import com.dpap.bookingapp.booking.room.Room;
//import com.dpap.bookingapp.booking.room.RoomService;
//import com.dpap.bookingapp.booking.room.RoomState;
//import com.dpap.bookingapp.booking.room.UpdateRoomRequest;
//import com.dpap.bookingapp.booking.room.dataaccess.RoomEntity;
//import com.dpap.bookingapp.booking.room.dto.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/rooms")
//public class RoomController {
//
//    private final RoomService roomService;
//    private final AuthenticationService authenticationService;
//
//
//    public RoomController(RoomService roomService, AuthenticationService authenticationService) {
//        this.roomService = roomService;
//        this.authenticationService = authenticationService;
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateReservationById(@PathVariable Long id, @RequestBody UpdateRoomRequest updateRoomRequest) {
//        roomService.updateRoomById(id, updateRoomRequest);
//        return ResponseEntity.status(204).build();
//    }
//
//
//
//    @DeleteMapping("/{roomId}")
//    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
//        roomService.removeRoom(RoomId.fromLong(roomId));
//        return ResponseEntity.status(201).build();
//    }
//
//    @PutMapping("/{roomId}/inactivation")
//    public ResponseEntity<?> disable(@PathVariable Long roomId) {
//        roomService.removeRoom(RoomId.fromLong(roomId));
//        return ResponseEntity.status(204).build();
//    }
//
//    @PutMapping("/enable")
//    public ResponseEntity<?> enable(Long roomId) {
//        roomService.enableRoom(RoomId.fromLong(roomId));
//        return ResponseEntity.status(204).build();
//    }
//
//    public ResponseEntity<List<Room>> getAllRooms(@PathVariable Long placeId) {
//        return ResponseEntity.ok(roomService.findRoomsByPlace(placeId));
//    }
//
//    @GetMapping("/{roomId}")
//    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
//        return ResponseEntity.ok(roomService.findRoomById(RoomId.fromLong(roomId)));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RoomEntity>> fetchAllRoomsWithFilters(
//            @RequestParam(required = false) Integer capacity,
//            @RequestParam(required = false) Long minPricePerNight,
//            @RequestParam(required = false) Long maxPricePerNight,
//            @RequestParam(required = false) RoomState state,
//            @RequestParam(required = false) Long placeId) {
//        return ResponseEntity.ok(roomService.findAllByFilters(new RoomFilter(
//                capacity,
//                minPricePerNight,
//                maxPricePerNight,
//                state,
//                placeId
//        )));
//    }
//}
