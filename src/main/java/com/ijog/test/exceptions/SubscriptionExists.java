package com.ijog.test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SubscriptionExists extends Exception {

    private static final long serialVersionUID = 2927544266255224066L;

    public SubscriptionExists(final String message) {
        super(message);
    }

    public SubscriptionExists(final Throwable ex) {
        super(ex);
    }
}
