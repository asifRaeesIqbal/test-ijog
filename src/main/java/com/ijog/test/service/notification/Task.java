package com.ijog.test.service.notification;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Task implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    //TODO: Use Spring Webflux to be more performant, could also use retry, fall back

    private String url;
    private RestTemplate restTemplate;

    public Task(final String url, final RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    // TODO: look into getting time at a specified zone??
    // TODO: What happens if the url called is not available... do we remove? continue? maybe using webflux after x attempts
    //       re remove the task.
    @Override
    public void run() {
        final String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        logger.info("Calling url {}, Thread {}, Time {}", url, Thread.currentThread().getName(), formattedTime);
        final Notification notification = Notification.builder().time(formattedTime).build();
        try {
            this.restTemplate.postForEntity(url, notification, String.class); // not interested in the return, assuming no content
        } catch (Exception ex) {
            // TODO: As above, we should do exponential back off and then perhaps remove the task
            logger.error("Making callback to URL failed", ex);
        }

    }

    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @EqualsAndHashCode
    static class Notification {
        private String time;
    }
}
