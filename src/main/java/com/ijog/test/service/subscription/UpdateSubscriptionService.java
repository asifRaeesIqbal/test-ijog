package com.ijog.test.service.subscription;

import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateSubscriptionService implements UpdateSubscription {

    private static final Logger logger = LoggerFactory.getLogger(UpdateSubscriptionService.class);

    private final SubscriptionRepository clockSubscriptionRepository;
    private final ClockNotificationService clockNotificationService;

    public UpdateSubscriptionService(final SubscriptionRepository clockSubscriptionRepository, final ClockNotificationService clockNotificationService) {
        this.clockSubscriptionRepository = clockSubscriptionRepository;
        this.clockNotificationService = clockNotificationService;
    }

    @Override
    public void update(final String url, final long frequencyInSec) throws SubscriptionNotFound {
        logger.info("Calling update for url: {}", url);
        try {
            this.clockSubscriptionRepository.updateSubscription(url, frequencyInSec);
            this.clockNotificationService.removeScheduledNotification(url);
            this.clockNotificationService.scheduleNotification(url, frequencyInSec);
        } catch (SubscriptionNotFoundInDB e) {
            throw new SubscriptionNotFound(e);
        }
    }
}
