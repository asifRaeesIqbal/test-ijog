package com.ijog.test.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class ClockNotificationService implements ClockNotification {

    private static final Logger logger = LoggerFactory.getLogger(ClockNotificationService.class);

    // TODO: IF a DB was being used, you would ideally load the map on startup using @postcontruct,
    // TODO: Could autowire the repository and pull all the subscriptions and load them into the taskschedular (protection against loss)

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final RestTemplate restTemplate;
    private final ConcurrentHashMap<String, ScheduledFuture<?>> notifcationsMap = new ConcurrentHashMap<>();

    @Autowired
    public ClockNotificationService(final ThreadPoolTaskScheduler threadPoolTaskScheduler, final RestTemplate restTemplate) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.restTemplate = restTemplate;
    }

    @Override
    public void scheduleNotification(final String url, final long frequencyInSec) {
        logger.info("Scheduling notification url: {} and frequency {}: ", url, frequencyInSec);
        final Task task = new Task(url, this.restTemplate);
        final long freqInMs = frequencyInSec * 1000;
        final ScheduledFuture<?> scheduledTask = threadPoolTaskScheduler.scheduleWithFixedDelay(task, freqInMs);
        notifcationsMap.put(url, scheduledTask);
    }

    @Override
    public void removeScheduledNotification(final String url) {
        logger.info("Remove Scheduling notification url: {} ", url);
        ScheduledFuture<?> scheduledTask = notifcationsMap.get(url);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            notifcationsMap.remove(url);
        }
    }
}
