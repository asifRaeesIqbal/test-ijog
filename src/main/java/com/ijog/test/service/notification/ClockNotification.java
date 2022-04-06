package com.ijog.test.service.notification;

// TODO: Add java docs
public interface ClockNotification {

    void scheduleNotification(String url, long frequencyInSec);

    void removeScheduledNotification(String url);
}
