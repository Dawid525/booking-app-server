package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.booking.place.PlaceService;
import com.dpap.bookingapp.booking.reservation.events.ReservationCancelled;
import com.dpap.bookingapp.booking.reservation.events.ReservationConfirmed;
import com.dpap.bookingapp.booking.reservation.events.ReservationEventPublisher;
import com.dpap.bookingapp.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationHostService {

    private final PlaceService placeService;
    private final ReservationRepository reservationRepository;
    private final ReservationEventPublisher reservationEventPublisher;

    public ReservationHostService(
            PlaceService placeService,
            ReservationRepository reservationRepository,
            ReservationEventPublisher reservationEventPublisher
    ) {
        this.placeService = placeService;
        this.reservationRepository = reservationRepository;
        this.reservationEventPublisher = reservationEventPublisher;
    }

    public void confirmReservation(Long userId, Long reservationId) {
        var reservation = findById(reservationId);
        if (!placeService.findPlaceResponseById(reservation.getPlaceId()).userId().equals(userId)) {
            throw new RuntimeException("There is no matched place");
        }
        reservation.confirm();
        reservationRepository.update(reservation.getId(), reservation.getValue(), reservation.getState());
        reservationEventPublisher.publish(new ReservationConfirmed(this, reservation));
    }

    public List<Reservation> findAllOffers(Long hostId) {
        return reservationRepository.findAllToAccept(hostId);
    }

    public void cancelOffer(Long reservationId, Long userId) {
        var reservation = findAllOffers(userId).stream().filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found reservation with id: " + reservationId));
        reservation.cancel(LocalDateTime.now());
        reservationRepository.update(reservation.getId(), reservation.getValue(), reservation.getState());
        reservationEventPublisher.publish(new ReservationCancelled(this, reservation));
    }

    Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .getOrElseThrow(() -> new RuntimeException("Not found Reservation " + id));
    }
}
