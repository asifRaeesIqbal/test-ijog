package com.ijog.test.repository.exceptions;

public class SubscriptionNotFoundInDB extends Exception {

    private static final long serialVersionUID = 29275442662333066L;

    public SubscriptionNotFoundInDB(final String message) {
            super(message);
    }
}

