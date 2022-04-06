package com.ijog.test.repository;

import com.ijog.test.repository.exceptions.SubscriptionExistsInDB;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;

// TODO: Add java docs
public interface SubscriptionRepository {

    boolean saveSubscription(final String url, final long frequencyInSec) throws SubscriptionExistsInDB;

    boolean updateSubscription(final String url, final long frequencyInSec) throws SubscriptionNotFoundInDB;

    boolean deleteSubscription(final String url) throws SubscriptionNotFoundInDB;

}
