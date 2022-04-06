package com.ijog.test.service.register;

import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import com.ijog.test.service.subscription.UpdateSubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// TODO: Add more tests
@ExtendWith(MockitoExtension.class)
public class UpdateSubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ClockNotificationService clockNotificationService;

    @InjectMocks
    private UpdateSubscriptionService updateSubscriptionService;

    @Test
    public void when_updating_notification_call_repository_and_scheduler() throws SubscriptionNotFoundInDB, SubscriptionNotFound {
        final String url = "http://localhost";
        final long freqInSec = 100;

        when(subscriptionRepository.updateSubscription(url, freqInSec)).thenReturn(true);
        doNothing().when(clockNotificationService).removeScheduledNotification(url);
        doNothing().when(clockNotificationService).scheduleNotification(url, freqInSec);

        updateSubscriptionService.update(url, freqInSec);

        verify(subscriptionRepository).updateSubscription(url, freqInSec);
        verify(clockNotificationService).removeScheduledNotification(url);
        verify(clockNotificationService).scheduleNotification(url, freqInSec);
    }
}
