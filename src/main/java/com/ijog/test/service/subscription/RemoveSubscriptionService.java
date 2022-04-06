package com.ijog.test.service.subscription;

import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveSubscriptionService implements RemoveSubscription {

    private static final Logger logger = LoggerFactory.getLogger(RemoveSubscriptionService.class);

    private final SubscriptionRepository clockSubscriptionRepository;
    private final ClockNotificationService clockNotificationService;

    @Autowired
    public RemoveSubscriptionService(final SubscriptionRepository clockSubscriptionRepository, final ClockNotificationService clockNotificationService) {
        this.clockSubscriptionRepository = clockSubscriptionRepository;
        this.clockNotificationService = clockNotificationService;
    }

    @Override
    public void unsubscribe(final String url) throws SubscriptionNotFound {
        logger.info("Calling delete for url: {}", url);
        try {
            this.clockSubscriptionRepository.deleteSubscription(url);
            this.clockNotificationService.removeScheduledNotification(url);
        } catch (SubscriptionNotFoundInDB e) {
            throw new SubscriptionNotFound(e);
        }
    }
}
