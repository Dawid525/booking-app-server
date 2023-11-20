package com.dpap.bookingapp.booking.place;

import com.dpap.bookingapp.auth.AuthenticationService;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceResponse;
import com.dpap.bookingapp.booking.place.room.UpdateRoomRequest;
import com.dpap.bookingapp.booking.place.room.dto.AddRoomRequest;
import com.dpap.bookingapp.timeslot.TimeSlot;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/places")
@Tag(name = "Places")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaceController {

    private final PlaceService placeService;
    private final AuthenticationService authenticationService;

    public PlaceController(PlaceService placeService, AuthenticationService authenticationService) {
        this.placeService = placeService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceResponse> findPlaceEntityById(@PathVariable Long placeId) {
        return ResponseEntity.ok(placeService.findPlaceById(placeId));
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> findAllPlaceEntities() {
        return ResponseEntity.ok(placeService.findAll());
    }

    @GetMapping("/filters")
    public ResponseEntity<List<PlaceResponse>> fetchAllPlaceEntitiesWithFilters(
            @RequestParam(required = false) String voivodeship,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) PlaceCategory category,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Long pricePerNight,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        var placeSearchFilter = PlaceSearchFilter.Builder.newBuilder()
                .voivodeship(voivodeship)
                .userId(userId)
                .street(street)
                .city(city)
                .category(category).build();
        var roomSearchFilter = RoomSearchFilter.Builder.newBuilder()
                .capacity(capacity)
                .pricePerNight(pricePerNight).build();
        if (from != null && to != null) {
            return ResponseEntity.ok(placeService.findAllByFilters(placeSearchFilter, roomSearchFilter, new TimeSlot(from, to)));
        }
        return ResponseEntity.ok(placeService.findAllByFilters(placeSearchFilter, roomSearchFilter));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PlaceResponse>> findAllPlaceEntitiesByUserId() {
        Long userId = authenticationService.getLoggedUser().id();
        return ResponseEntity.ok(placeService.findAllByFilters(PlaceSearchFilter.Builder.newBuilder().userId(userId).build()));
    }

    @PostMapping
    public ResponseEntity<?> addPlaceEntity(@RequestBody @Valid AddPlaceRequest request) {
        placeService.addPlace(request, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaceEntityById(@PathVariable Long id) {
        placeService.deletePlaceByIdAndUserId(id, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}/rooms/{roomId}")
    public ResponseEntity<?> deleteRoomEntityById(@PathVariable Long id, @PathVariable Long roomId) {
        placeService.deleteRoomByPlaceIdAndUserIdAndRoomId(authenticationService.getLoggedUser().id(), id, roomId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}/rooms/{roomId}")
    public ResponseEntity<?> updateRoomEntityById(@PathVariable Long id, @PathVariable Long roomId, @RequestBody UpdateRoomRequest updateRoomRequest) {
        placeService.updateRoomById(authenticationService.getLoggedUser().id(), id, roomId, updateRoomRequest);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/{placeId}/rooms")
    public ResponseEntity<?> addRoom(@PathVariable Long placeId, @RequestBody AddRoomRequest request) {
        placeService.addRoom(request, placeId, authenticationService.getLoggedUser().id());
        return ResponseEntity.status(201).build();
    }

}
