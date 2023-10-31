package com.dpap.bookingapp.notification;

public interface NotificationService {

    public void sendNotification(String recipientEmail, NotificationTemplate template);

    public void sendNotification(Long userId, NotificationTemplate template);


}
