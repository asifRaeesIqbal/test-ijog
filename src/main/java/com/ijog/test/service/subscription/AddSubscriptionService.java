package com.ijog.test.service.subscription;

import com.ijog.test.exceptions.SubscriptionExists;
import com.ijog.test.repository.SubscriptionRepository;
import com.ijog.test.repository.exceptions.SubscriptionExistsInDB;
import com.ijog.test.service.notification.ClockNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddSubscriptionService implements AddSubscription {

    private static final Logger logger = LoggerFactory.getLogger(AddSubscriptionService.class);

    private final SubscriptionRepository clockSubscriptionRepository;
    private final ClockNotificationService clockNotificationService;

    public AddSubscriptionService(final SubscriptionRepository clockSubscriptionRepository, final ClockNotificationService clockNotificationService) {
        this.clockSubscriptionRepository = clockSubscriptionRepository;
        this.clockNotificationService = clockNotificationService;
    }

    @Override
    public void subscribe(final String url, final long frequencyInSec) throws SubscriptionExists {
        logger.info("Calling Add for  url: {}", url);
        try {
            this.clockSubscriptionRepository.saveSubscription(url, frequencyInSec);
            this.clockNotificationService.scheduleNotification(url, frequencyInSec);
        } catch (SubscriptionExistsInDB e) {
            throw new SubscriptionExists(e);
        }
    }
}
