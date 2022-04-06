package com.ijog.test.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationRequestTest {

    //TODO: Add further tests for validating against regex for url
    //TODO: Improve testing check message

    private Validator validator;

    @BeforeEach
    public void before() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_flag_no_errors_when_valid_request() {
        final NotificationRequest notificationRequest = new NotificationRequest("http://localhost", 5);

        final Set<ConstraintViolation<NotificationRequest>> violationSet = this.validator.validate(notificationRequest);

        assertEquals(0, violationSet.size());
    }

    @Test
    public void should_flag_error_when_url_is_null() {
        final NotificationRequest notificationRequest = new NotificationRequest(null, 5);

        final Set<ConstraintViolation<NotificationRequest>> violationSet = this.validator.validate(notificationRequest);

        assertEquals(1, violationSet.size());
    }

    @Test
    public void should_flag_error_when_frequency_is_invalid() {
        final NotificationRequest notificationRequest = new NotificationRequest("http://localhost", 0);

        final Set<ConstraintViolation<NotificationRequest>> violationSet = this.validator.validate(notificationRequest);

        assertEquals(1, violationSet.size());
    }

    @Test
    public void should_flag_error_when_frequency_is_invalid_greater_than_4_hours() {
        final NotificationRequest notificationRequest = new NotificationRequest("http://localhost", 15400);

        final Set<ConstraintViolation<NotificationRequest>> violationSet = this.validator.validate(notificationRequest);

        assertEquals(1, violationSet.size());
    }
}
