package com.ijog.test.repository.exceptions;

public class SubscriptionExistsInDB extends Exception {

    private static final long serialVersionUID = 2344544266255224066L;

    public SubscriptionExistsInDB(final String message) {
        super(message);
    }
}
