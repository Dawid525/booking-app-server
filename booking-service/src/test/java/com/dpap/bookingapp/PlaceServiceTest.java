package com.dpap.bookingapp;

import com.dpap.bookingapp.availability.service.AvailabilityService;
import com.dpap.bookingapp.booking.place.PlaceCategory;
import com.dpap.bookingapp.booking.place.PlaceSearchFilter;
import com.dpap.bookingapp.booking.place.PlaceService;
import com.dpap.bookingapp.booking.place.dataaccess.Address;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceEntity;
import com.dpap.bookingapp.booking.place.dataaccess.PlaceRepository;
import com.dpap.bookingapp.booking.place.room.RoomService;
import com.dpap.bookingapp.users.UserEntity;
import com.dpap.bookingapp.users.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PlaceServiceTest {


    @Mock
    AvailabilityService availabilityService;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RoomService roomService;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    private PlaceService placeService;

    @Test
    public void shouldReturnPlaceById() {
        var place = new PlaceEntity();
        place.setId(1L);
        place.setName("Hotelik");
        place.setCategory(PlaceCategory.HOTEL);
        place.setAddress(new Address("Magdaleny Brzeskiej", "Lublin", "Poland", "42", "lubelskie"));
        place.setDescription("Description");
        place.setId(1L);
        place.setUser(new UserEntity());
        placeRepository.save(place);
        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));

        var result = placeService.findPlaceById(1L);

        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("Hotelik", result.name());
    }

    @Test
    public void shouldThrowExceptionWhenPlaceDoesNotExist() {
        when(placeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> placeService.findPlaceById(1L));
    }

//    @Test
//    public void shouldI() {
//        when();
//        Query query = Mockito.mock(Query.class);
//
//        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
//        Mockito.when(query.getResultList()).thenReturn(new ArrayList<>());
//        var result = placeService.findAllByFilters(PlaceSearchFilter.Builder.newBuilder().category(PlaceCategory.HOTEL).build());
//        System.out.println(result);
//    }

}
