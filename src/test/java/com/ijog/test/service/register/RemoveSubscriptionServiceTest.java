package com.ijog.test.service.register;

import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import com.ijog.test.service.subscription.RemoveSubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// TODO: Add more tests
@ExtendWith(MockitoExtension.class)
public class RemoveSubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ClockNotificationService clockNotificationService;

    @InjectMocks
    private RemoveSubscriptionService removeSubscriptionService;

    @Test
    public void when_removing_notification_call_repository_and_scheduler() throws SubscriptionNotFoundInDB, SubscriptionNotFound {
        final String url = "http://localhost";

        when(subscriptionRepository.deleteSubscription(url)).thenReturn(true);
        doNothing().when(clockNotificationService).removeScheduledNotification(url);

        removeSubscriptionService.unsubscribe(url);

        verify(subscriptionRepository).deleteSubscription(url);
        verify(clockNotificationService).removeScheduledNotification(url);
    }
}
