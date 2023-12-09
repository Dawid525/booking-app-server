package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.booking.place.PlaceService;
import com.dpap.bookingapp.notification.NotificationService;
import com.dpap.bookingapp.notification.NotificationTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationHostService {

    private final PlaceService placeService;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ReservationHostService(PlaceService placeService, ReservationRepository reservationRepository, NotificationService notificationService) {
        this.placeService = placeService;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    public void confirmReservation(Long userId, Long reservationId) {
        var reservation = findById(reservationId);
        if (!placeService.findPlaceResponseById(reservation.getPlaceId()).userId().equals(userId)) {
            throw new RuntimeException("There is no matched place");
        }
        reservation.confirm();
        reservationRepository.updateState(reservation.getId(), reservation.getState());
        String content = """
                Host has confirmed your reservation.
                """;
        notificationService.sendNotification(
                reservation.getUserId(),
                new NotificationTemplate(
                        content,
                        reservation.getId(),
                        reservation.getCheckIn(),
                        reservation.getCheckOut()
                )
        );
    }

    public List<Reservation> findAllOffers(Long hostId) {
        return reservationRepository.findAllToAccept(hostId);
    }
    public void cancelOffer(Long reservationId, Long userId) {
        var reservation = findAllOffers(userId).stream().filter(r -> r.getId().equals(reservationId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Not found reservation with id: " + reservationId));
        reservation.cancel(LocalDateTime.now());
        reservationRepository.updateState(reservation.getId(), reservation.getState());
        String content = """
                Host has rejected your reservation.
                """;
        notificationService.sendNotification(
                reservation.getUserId(),
                new NotificationTemplate(
                        content,
                        reservation.getId(),
                        reservation.getCheckIn(),
                        reservation.getCheckOut()
                )
        );
    }
    Reservation findById(Long id) {
        return reservationRepository.findById(id).getOrElseThrow(() -> new RuntimeException("Not found Reservation " + id));
    }
}
