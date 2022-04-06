package com.ijog.test.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NotificationRequest {

    //TODO: Add validation for url with regex

    public static final String PLEASE_PROVIDE_A_VALID_URL = "Please provide a valid URL";
    public static final String MINIMUM_MAXIMUM_ALLOWED_SECONDS = "Please provide a valid value for seconds between 4 and 14400(4 hours)";

    @NotEmpty(message = PLEASE_PROVIDE_A_VALID_URL)
    private String url;
    @Min(value = 5, message = MINIMUM_MAXIMUM_ALLOWED_SECONDS)
    @Max(value = 14400, message = MINIMUM_MAXIMUM_ALLOWED_SECONDS)
    private long frequency;

}
