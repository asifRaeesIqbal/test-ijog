package com.ijog.test.web;

import com.ijog.test.exceptions.SubscriptionExists;
import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.service.subscription.AddSubscription;
import com.ijog.test.service.subscription.RemoveSubscription;
import com.ijog.test.service.subscription.UpdateSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/clock")
public class ClockNotificationController {

    private static final Logger logger = LoggerFactory.getLogger(ClockNotificationController.class);

    private AddSubscription addSubscriptionService;
    private RemoveSubscription removeSubscriptionService;
    private UpdateSubscription updateSubscriptionService;

    @Autowired
    public ClockNotificationController(final AddSubscription addSubscriptionService, final RemoveSubscription removeSubscriptionService,
                                       final UpdateSubscription updateSubscriptionService) {
        this.addSubscriptionService = addSubscriptionService;
        this.removeSubscriptionService = removeSubscriptionService;
        this.updateSubscriptionService = updateSubscriptionService;
    }

    @PostMapping("/notification")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> register(@Valid @RequestBody final NotificationRequest registerNotificationRequest) throws SubscriptionExists {
        logger.info("Register clock callback..");

        this.addSubscriptionService.subscribe(registerNotificationRequest.getUrl(), registerNotificationRequest.getFrequency());

        return ResponseEntity.created(URI.create("/clock/notification/"+registerNotificationRequest.getUrl()))
                .header("Content-Length", "0").build();
    }

    @PutMapping("/notification")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Object> update(@Valid @RequestBody final NotificationRequest updateNotificationRequest) throws SubscriptionNotFound {
        logger.info("Updating the notification frequency..");

        this.updateSubscriptionService.update(updateNotificationRequest.getUrl(), updateNotificationRequest.getFrequency());

        return ResponseEntity.ok().header("Content-Length", "0").build();
    }

    @DeleteMapping("/notification")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deregister(@RequestParam final String url) throws SubscriptionNotFound {
        logger.info("Deregister clock notification...");

        this.removeSubscriptionService.unsubscribe(url);

        return ResponseEntity.noContent().header("Content-Length", "0").build();
    }
}
