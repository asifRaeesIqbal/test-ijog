package com.ijog.test.service.subscription;


import com.ijog.test.exceptions.SubscriptionExists;

public interface AddSubscription {

    /**
     * Simple interface used to register/subscribe the url for clock notification, using a fixed frequency as specified
     * by the frequencyInSec
     *
     * @param url                -- url to call
     * @param frequencyInSec      -- the frequency between each notification
     */
    void subscribe(final String url, final long frequencyInSec) throws SubscriptionExists;
}
