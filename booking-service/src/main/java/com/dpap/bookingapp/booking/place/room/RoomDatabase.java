//package com.dpap.bookingapp.booking.room;
//
//import com.dpap.bookingapp.booking.room.dto.RoomFilter;
//import com.dpap.bookingapp.booking.room.dto.RoomId;
//import io.vavr.control.Option;
//
//import java.util.List;
//
//public interface RoomDatabase {
//
//    Option<Room> findRoomById(RoomId id);
//    List<Room> findAllByPlaceId(Long placeId);
//    RoomId addRoom(Room room);
//    void assignRoomToPlace(Long placeId, Long id);
//    RoomState fetchRoomState(RoomId roomId);
//    List<Room> findAll();
//
//    List<Room> findAllByVoivodeship(String voivodeship);
//
//    List<Room> findAllByCity(String city);
//
//    List<Room> findAllByCityAndStreet(String city, String street);
//
//    List<Room> findAllByFilters(RoomFilter roomFilter);
//
//    void updateState(Long id, RoomState state);
//
//    void updateRoom(Room room);
//}
