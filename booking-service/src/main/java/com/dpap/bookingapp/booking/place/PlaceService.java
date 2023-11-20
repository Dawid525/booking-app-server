package com.dpap.bookingapp.booking.place;

import com.dpap.bookingapp.availability.service.AvailabilityService;
import com.dpap.bookingapp.booking.common.JsonToCollectionMapper;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceEntity;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceRepository;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceResponse;
import com.dpap.bookingapp.booking.place.exception.NotFoundPlaceException;
import com.dpap.bookingapp.booking.place.room.RoomService;
import com.dpap.bookingapp.booking.place.room.UpdateRoomRequest;
import com.dpap.bookingapp.booking.place.room.dataaccess.RoomEntity;
import com.dpap.bookingapp.booking.place.room.dto.AddRoomRequest;
import com.dpap.bookingapp.booking.place.room.dto.RoomDTO;
import com.dpap.bookingapp.timeslot.TimeSlot;
import com.dpap.bookingapp.users.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dpap.bookingapp.booking.common.JsonToCollectionMapper.deserialize;

@Service
public class PlaceService {

    private final AvailabilityService availabilityService;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;
    @PersistenceContext
    private EntityManager entityManager;

    public PlaceService(AvailabilityService availabilityService,
                        PlaceRepository placeRepository, UserRepository userRepository, RoomService roomService) {
        this.availabilityService = availabilityService;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.roomService = roomService;
    }

    @Transactional
    public void addPlace(AddPlaceRequest request, Long userId) {
        var user = userRepository.findEntityById(userId)
                .orElseThrow(() -> new RuntimeException("Not found user with id:" + userId));
        var place = Place.fromRequest(request, userId);
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setDescription(place.getDescription());
        placeEntity.setName(place.getName());
        placeEntity.setCategory(place.getCategory());
        placeEntity.setUser(user);
        placeEntity.setDescription(place.getDescription());
        placeEntity.setAddress(request.address());
        placeRepository.save(placeEntity);
        List<RoomEntity> savedRooms = new ArrayList<>();
        for (AddRoomRequest roomRequest : request.rooms()) {
            savedRooms.add(roomService.addRoom(roomRequest, placeEntity));
        }
        savedRooms.forEach(placeEntity::addRoom);
    }

    public List<PlaceResponse> findAllByFilters(PlaceSearchFilter filter) {
        if (filter.isEmpty()) {
            return placeRepository.findAll().stream().map(this::mapToPlaceResponse).toList();
        }
        TypedQuery<PlaceEntity> typedQuery = entityManager.createQuery(prepareQueryCriteriaBy(filter));
        return typedQuery.getResultList().stream().map(this::mapToPlaceResponse).toList();
    }

    public List<PlaceResponse> findAllByFilters(PlaceSearchFilter filter, RoomSearchFilter roomSearchFilter, TimeSlot timeSlot) {
        List<PlaceResponse> places = new ArrayList<>();
        for (PlaceResponse place : findAllByFilters(filter, roomSearchFilter)) {
            List<RoomDTO> rooms = new ArrayList<>();
            place.rooms().forEach(
                    roomDTO -> {
                        if (availabilityService.isObjectAvailable(roomDTO.id(), timeSlot)) {
                            rooms.add(roomDTO);
                        }
                    }
            );
            places.add(place.withRooms(rooms));
        }
        return places;
    }

    public List<PlaceResponse> findAllByFilters(PlaceSearchFilter filter, RoomSearchFilter roomSearchFilter) {
        List<PlaceEntity> places;
        if (filter.isEmpty()) {
            places = placeRepository.findAll();
        } else {
            TypedQuery<PlaceEntity> typedQuery = entityManager.createQuery(prepareQueryCriteriaBy(filter));
            places = typedQuery.getResultList();
        }
        if (roomSearchFilter.isEmpty()) {
            return places.stream().map(this::mapToPlaceResponse).toList();
        }
        return filterPlacesByRoomParams(places, roomSearchFilter);
    }

    private List<PlaceResponse> filterPlacesByRoomParams(List<PlaceEntity> places, RoomSearchFilter roomSearchFilter) {
        return places.stream()
                .map(place -> this.mapToPlaceResponseWithRooms(place, place.findRoomsByFilter(roomSearchFilter)))
                .toList();
    }

    private CriteriaQuery<PlaceEntity> prepareQueryCriteriaBy(PlaceSearchFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlaceEntity> criteriaQuery = cb.createQuery(PlaceEntity.class);
        Root<PlaceEntity> from = criteriaQuery.from(PlaceEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        filter.getCategory().map(category -> predicates.add(cb.equal(from.get("category"), category)));
        filter.getVoivodeship().map(voivodeship -> predicates.add(cb.equal(from.get("address").get("voivodeship"), voivodeship)));
        filter.getCity().map(city -> predicates.add(cb.equal(from.get("address").get("city"), city)));
        filter.getStreet().map(street -> predicates.add(cb.equal(from.get("address").get("street"), street)));
        filter.getUserId().map(userId -> predicates.add(cb.equal(from.get("user").get("id"), userId)));

        if (predicates.isEmpty()) {
            return criteriaQuery;
        }
        return criteriaQuery.select(from).where(cb.and(predicates.toArray(new Predicate[0])));
    }

    public PlaceResponse findPlaceById(Long placeId) {
        return placeRepository
                .findById(placeId)
                .map(this::mapToPlaceResponse)
                .orElseThrow(() -> new IllegalArgumentException("Not found place with id"));
    }

    public List<PlaceResponse> findAll() {
        return placeRepository.findAll()
                .stream()
                .map(this::mapToPlaceResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRoom(AddRoomRequest request, Long placeId, Long userId) {
        var place = entityManager
                .createQuery(
                        "SELECT p FROM PlaceEntity p WHERE p.id = :placeId AND p.user.id = :userId ",
                        PlaceEntity.class
                )
                .setParameter("placeId", placeId)
                .setParameter("userId", userId)
                .getResultList()
                .stream().findFirst()
                .orElseThrow(() -> new NotFoundPlaceException("Not found place with id: " + placeId));
        var room = roomService.addRoom(request, place);
        place.addRoom(room);
    }

    public void deletePlaceByIdAndUserId(Long id, Long userId) {
        placeRepository.deleteAllByIdAndUserId(id, userId);
    }

    private RoomDTO mapToRoomResponse(RoomEntity roomEntity) {
        return new RoomDTO(
                roomEntity.getId(),
                roomEntity.getCapacity(), roomEntity.getState(), roomEntity.getPlace().getId(),
                roomEntity.getPricePerNight(), roomEntity.getDescription(), roomEntity.getName(),
                deserialize(roomEntity.getFacilities()).stream().toList()
        );
    }

    private PlaceResponse mapToPlaceResponse(PlaceEntity placeEntity) {
        return new PlaceResponse(
                placeEntity.getId(),
                placeEntity.getName(),
                placeEntity.getDescription(),
                placeEntity.getAddress(),
                placeEntity.getCategory(),
                placeEntity.getUser().getId(),
                placeEntity.getRooms().stream().map(this::mapToRoomResponse).collect(Collectors.toList()),
                deserialize(placeEntity.getFacilities()).stream().toList()
        );
    }


    private PlaceResponse mapToPlaceResponseWithRooms(PlaceEntity placeEntity, List<RoomEntity> rooms) {
        return new PlaceResponse(
                placeEntity.getId(),
                placeEntity.getName(),
                placeEntity.getDescription(),
                placeEntity.getAddress(),
                placeEntity.getCategory(),
                placeEntity.getUser().getId(),
                rooms.stream().map(this::mapToRoomResponse).collect(Collectors.toList()),
                deserialize(placeEntity.getFacilities()).stream().toList()
        );
    }

    @Transactional
    public void deleteRoomByPlaceIdAndUserIdAndRoomId(Long userId, Long placeId, Long roomId) {
        var place = entityManager.createQuery("SELECT  p FROM PlaceEntity p join RoomEntity r" +
                        " on r.place.id = p.id WHERE p.user.id = :userId AND p.id = :placeId ", PlaceEntity.class)
                .setParameter("placeId", placeId)
                .setParameter("userId", userId)
                .getSingleResult();
        var room = place.getRooms().stream().filter(roomEntity -> roomEntity.getId().equals(roomId)).findFirst().orElseThrow(() -> new RuntimeException("Not found room with id: " + roomId));
        entityManager.remove(room);
        place.removeRoom(room);
    }

    @Transactional
    public void updateRoomById(Long userId, Long placeId, Long roomId, UpdateRoomRequest request) {
        var room = entityManager.createQuery(
                        "SELECT r FROM RoomEntity r where r.id = :roomId AND r.place.id = :placeId AND r.place.user.id = :userId", RoomEntity.class)
                .setParameter("placeId", placeId)
                .setParameter("roomId", roomId)
                .setParameter("userId", userId)
                .getSingleResult();
        if (request.capacity() != null)
            room.setCapacity(request.capacity());

        var facilities = JsonToCollectionMapper.deserialize(room.getFacilities());
        if (request.facilities() != null) {
            facilities.addAll(request.facilities());
            room.setFacilities(JsonToCollectionMapper.serialize(facilities));
        }
        if (request.pricePerNight() != null) {
            room.setPricePerNight(request.pricePerNight());
        }
        if (request.capacity() != null) {
            room.setCapacity(request.capacity());
        }
        if (request.name() != null) {
            room.setName(request.name());
        }
        if (request.description() != null) {
            room.setDescription(request.description());
        }
        entityManager.merge(room);
    }
}
