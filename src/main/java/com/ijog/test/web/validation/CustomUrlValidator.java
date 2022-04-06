package com.ijog.test.web.validation;

import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// TODO: Need to wire this in and test this.
public class CustomUrlValidator implements ConstraintValidator<UrlValidation, String>
{
    public boolean isValid(final String url, ConstraintValidatorContext cxt) {
        final UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }
}
