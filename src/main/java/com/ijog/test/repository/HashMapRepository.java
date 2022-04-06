package com.ijog.test.repository;

import com.ijog.test.repository.exceptions.SubscriptionExistsInDB;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class HashMapRepository implements SubscriptionRepository {

    ConcurrentHashMap<String, Long> registrations = new ConcurrentHashMap<>();

    @Override
    public boolean saveSubscription(final String url, final long frequencyInSec) throws SubscriptionExistsInDB {
        if(this.registrations.get(url) != null) {
            throw new SubscriptionExistsInDB("Registration exists");
        }
        this.registrations.put(url.toLowerCase(), frequencyInSec);
        return true;
    }

    @Override
    public boolean updateSubscription(final String url, final long frequencyInSec) throws SubscriptionNotFoundInDB {
        if(this.registrations.get(url) != null) {
            this.registrations.put(url, frequencyInSec);
            return true;
        }
        throw new SubscriptionNotFoundInDB("Registration does not exist");
    }

    @Override
    public boolean deleteSubscription(final String url) {
        this.registrations.remove(url);
        return true;
    }
}
