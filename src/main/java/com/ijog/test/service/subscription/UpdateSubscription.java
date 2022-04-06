package com.ijog.test.service.subscription;

import com.ijog.test.exceptions.SubscriptionNotFound;

// TODO: Add java docs
public interface UpdateSubscription {

    void update(String url, long frequencyInSec) throws SubscriptionNotFound;
}
