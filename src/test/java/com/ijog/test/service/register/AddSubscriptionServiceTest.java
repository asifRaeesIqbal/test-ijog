package com.ijog.test.service.register;

import com.ijog.test.exceptions.SubscriptionExists;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionExistsInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import com.ijog.test.service.subscription.AddSubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// TODO: Add more tests
@ExtendWith(MockitoExtension.class)
public class AddSubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ClockNotificationService clockNotificationService;

    @InjectMocks
    private AddSubscriptionService addSubscriptionService;

    @Test
    public void when_registering_notification_call_repository_and_scheduler() throws SubscriptionExistsInDB, SubscriptionExists {
        final String url = "http://localhost";
        final long freqInSec = 10;

        when(subscriptionRepository.saveSubscription(url, freqInSec)).thenReturn(true);
        doNothing().when(clockNotificationService).scheduleNotification(url, freqInSec);

        addSubscriptionService.subscribe(url, freqInSec);

        verify(subscriptionRepository).saveSubscription(url, freqInSec);
        verify(clockNotificationService).scheduleNotification(url, freqInSec);
    }
}
