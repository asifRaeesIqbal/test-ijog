package com.ijog.test.service.subscription;

import com.ijog.test.exceptions.SubscriptionNotFound;

public interface RemoveSubscription {

    /**
     * Provides a URL previously registered/subscribed.  Should error if the client URL is not registered for callbacks.
     * Once called, callbacks to that URL should stop
     *
     * @param url
     */
    void unsubscribe(final String url) throws SubscriptionNotFound;
}
