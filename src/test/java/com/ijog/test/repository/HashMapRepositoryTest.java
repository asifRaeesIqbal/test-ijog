package com.ijog.test.repository;

import com.ijog.test.repository.exceptions.SubscriptionExistsInDB;
import com.ijog.test.repository.exceptions.SubscriptionNotFoundInDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Add more tests to test exception thrown
public class HashMapRepositoryTest {

    private HashMapRepository subscriptionRepository;

    @BeforeEach
    public void before() {
        this.subscriptionRepository = new HashMapRepository();
    }

    @Test
    public void should_add_subscription_to_repository_when_it_is_new() throws SubscriptionExistsInDB {
        final String url = "http://localhost";
        final long freqInSec = 10;

        this.subscriptionRepository.saveSubscription(url, freqInSec);

        assertTrue(this.subscriptionRepository.registrations.containsKey(url));
        assertEquals(freqInSec, this.subscriptionRepository.registrations.get(url));
    }

    @Test
    public void should_delete_subscription_successfully() throws SubscriptionExistsInDB {
        final String url = "http://localhost";
        final long freqInSec = 10;

        this.subscriptionRepository.saveSubscription(url, freqInSec);

        assertTrue(this.subscriptionRepository.registrations.containsKey(url));

        this.subscriptionRepository.deleteSubscription(url);

        assertFalse(this.subscriptionRepository.registrations.containsKey(url));
    }

    @Test
    public void should_update_subscription_successfully() throws SubscriptionExistsInDB, SubscriptionNotFoundInDB {
        final String url = "http://localhost";
        final long freqInSec = 10;

        this.subscriptionRepository.saveSubscription(url, freqInSec);

        assertTrue(this.subscriptionRepository.registrations.containsKey(url));
        assertEquals(freqInSec, this.subscriptionRepository.registrations.get(url));

        this.subscriptionRepository.updateSubscription(url, 20);

        assertTrue(this.subscriptionRepository.registrations.containsKey(url));
        assertEquals(20, this.subscriptionRepository.registrations.get(url));
    }
}
