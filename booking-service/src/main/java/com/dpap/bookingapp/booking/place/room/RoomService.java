package com.dpap.bookingapp.booking.place.room;

import com.dpap.bookingapp.booking.common.JsonToCollectionMapper;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceEntity;
import com.dpap.bookingapp.booking.place.room.dataaccess.RoomEntity;
import com.dpap.bookingapp.booking.place.room.dataaccess.RoomRepository;
import com.dpap.bookingapp.booking.place.room.dto.AddRoomRequest;
import com.dpap.bookingapp.booking.place.room.dto.RoomId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RoomService {

    private final RoomRepository roomDatabase;

    public RoomService(RoomRepository roomRepository) {
        this.roomDatabase = roomRepository;
    }


    private boolean checkName(String name) {
        return name.length() > 4;
    }

    public RoomEntity addRoom(AddRoomRequest request, PlaceEntity placeEntity) {
        var room = Room.fromRequest(request);

        RoomEntity roomEntity = new RoomEntity(
                room.getCapacity(),
                room.getState(),
                room.getPricePerNight(), room.getDescription(), room.getName(), room.getFacilities(),
                placeEntity
        );
        return roomDatabase.save(roomEntity);
    }
//    public void assignRoom(Long placeId, RoomId roomId) {
//        Room room = findRoomById(roomId);
//        room.assignToPlace(placeId);
//        roomDatabase.assignRoomToPlace(placeId, room.getId().getId());
//    }

//    public void removeRoom(RoomId roomId) {
//        Room room = findRoomById(roomId);
//        room.disableRoom();
//        roomDatabase.updateState(roomId.getId(), room.getState());
//    }
//
//    public void enableRoom(RoomId roomId) {
//        Room room = findRoomById(roomId);
//        room.enableRoom();
//        roomDatabase.updateState(roomId.getId(), room.getState());
//    }

    //    public List<Room> findRoomsByPlace(Long placeId) {
//        return roomDatabase.findAllByPlaceId(placeId);
//    }
//
//
//    public List<Room> findAll() {
//        return roomDatabase.findAll();
//    }
//
//    public List<Room> findAllByFilters(RoomFilter filter) {
//        return roomDatabase.findAllByFilters(filter);
//    }
//


    //
//    @Transactional
//    public void updateRoomById(Long userId, Long roomId, UpdateRoomRequest request) {
//        var room = findRoomByIdAndUserId(RoomId.fromLong(roomId), userId);
//        room.setCapacity(request.capacity());
//        var facilities = JsonToCollectionMapper.deserialize(room.getFacilities());
//        request.facilities().forEach(facilities::add);
//        room.setFacilities(JsonToCollectionMapper.serialize(facilities));
//        room.setCapacity(request.pricePerNight());
//        room.setName(request.name());
//        room.setDescription(request.description());
//    }

    private void findRoomByIdAndUserId(RoomId fromLong, Long userId) {
    }

    public Optional<RoomEntity> findRoomById(RoomId roomId) {
        return roomDatabase.findById(roomId.getId());
    }

//    public void addRoomDetails(Long roomId, AddRoomDetailsRequest request) {
//        RoomDetails roomDetails = new RoomDetails(
//                roomId,
//                request.name(),
//                request.description(),
//                request.imageUrl()
//        );
//        roomDetails.addFacilities(Set.of(
//                FacilityType.COFFEE,
//                FacilityType.AIR_CONDITIONING
//        ));
//        repository.save(roomDetails);
//    }

//    public RoomDetailsDTO roomDetailsById(Long id) {
//        var roomDetails = repository.findById(id);
//        return
//                new RoomDetailsDTO(
//                        roomDetails.getId(),
//                        roomDetails.getRoomId(),
//                        roomDetails.getName(),
//                        roomDetails.getDescription(),
//                        roomDetails.getImageUrl(),
//                        roomDetails.getFacilityTypes()
//                );
//    }

//    public List<RoomDetailsDTO> getRoomDetailsDTOForRoomIds(List<Long> roomIds) {
//        return repository.findByRoomIds(roomIds)
//                .stream().map(
//                        roomDetails -> new RoomDetailsDTO(
//                                roomDetails.getId(),
//                                roomDetails.getRoomId(),
//                                roomDetails.getName(),
//                                roomDetails.getDescription(),
//                                roomDetails.getImageUrl(),
//                                roomDetails.getFacilityTypes()
//                        )
//                ).toList();
//    }
}
