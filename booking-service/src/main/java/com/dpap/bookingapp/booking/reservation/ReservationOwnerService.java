package com.dpap.bookingapp.booking.reservation;

import com.dpap.bookingapp.booking.place.PlaceService;
import com.dpap.bookingapp.notification.NotificationService;
import com.dpap.bookingapp.notification.NotificationTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationOwnerService {

    private final PlaceService placeService;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ReservationOwnerService(PlaceService placeService, ReservationRepository reservationRepository, NotificationService notificationService) {
        this.placeService = placeService;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    public void confirmReservation(Long userId, Long reservationId) {
        var reservation = findById(reservationId);
        if (!placeService.findPlaceById(reservation.getPlaceId()).userId().equals(userId)) {
            throw new RuntimeException("There is no matched place");
        }

        reservation.confirm();
        reservationRepository.updateState(reservation.getId(), reservation.getState());
        String content = """
                Host has confirmed your reservation.
                """;
        notificationService.sendNotification(userId,
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
