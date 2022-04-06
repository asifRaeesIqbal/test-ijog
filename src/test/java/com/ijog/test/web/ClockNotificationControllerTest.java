package com.ijog.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ijog.test.exceptions.SubscriptionExists;
import com.ijog.test.exceptions.SubscriptionNotFound;
import com.ijog.test.service.subscription.AddSubscription;
import com.ijog.test.service.subscription.RemoveSubscription;
import com.ijog.test.service.subscription.UpdateSubscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClockNotificationController.class)
public class ClockNotificationControllerTest {

    @MockBean
    private AddSubscription addSubscriptionService;

    @MockBean
    private UpdateSubscription updateSubscriptionService;

    @MockBean
    private RemoveSubscription removeSubscriptionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_201_when_valid_post_request_is_made() throws Exception {
        final String url = "http://localhost:8080/test";
        final long frequencyInSec = 10l;
        final String requestJson = createRequestJson(url, frequencyInSec);

        doNothing().when(addSubscriptionService).subscribe(url, frequencyInSec);

        this.mockMvc.perform(post("/clock/notification")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/clock/notification/"+url))
                .andReturn();

        verify(addSubscriptionService, times(1)).subscribe(url, frequencyInSec);
    }

    @Test
    public void should_return_400_when_already_subscribed_post_request_is_made() throws Exception {
        final String url = "http://localhost:8080/test";
        final long frequencyInSec = 10l;
        final String requestJson = createRequestJson(url, frequencyInSec);

        doThrow(new SubscriptionExists("ALREADY REGISTERED")).when(this.addSubscriptionService).subscribe(url,frequencyInSec);

        this.mockMvc.perform(post("/clock/notification")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(addSubscriptionService, times(1)).subscribe(url, frequencyInSec);
    }

    @Test
    public void should_return_200_when_valid_updated_request_is_made() throws Exception {
        final String url = "http://localhost:8080/test";
        final long frequencyInSec = 10l;
        final String requestJson = createRequestJson(url, frequencyInSec);

        doNothing().when(updateSubscriptionService).update(url, frequencyInSec);

        this.mockMvc.perform(put("/clock/notification")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        verify(updateSubscriptionService, times(1)).update(url, frequencyInSec);
    }

    @Test
    public void should_return_400_when_non_existing_update_request_is_made() throws Exception {
        final String url = "http://localhost:8080/test";
        final long frequencyInSec = 10l;
        final String requestJson = createRequestJson(url, frequencyInSec);

        doThrow(new SubscriptionNotFound("NOT FOUND!")).when(updateSubscriptionService).update(url, frequencyInSec);

        this.mockMvc.perform(put("/clock/notification")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(updateSubscriptionService, times(1)).update(url, frequencyInSec);
    }

    @Test
    public void should_return_200_when_unsubscribe_request_is_made() throws Exception {
        final String url = "http://localhost:8080";

        doNothing().when(this.removeSubscriptionService).unsubscribe(url);

        this.mockMvc.perform(delete("/clock/notification")
                        .param("url", url))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(removeSubscriptionService, times(1)).unsubscribe(url);
    }

    private String createRequestJson(final String url, final Long frequencyInSec) throws JsonProcessingException {
        NotificationRequest model = new NotificationRequest();
        model.setFrequency(frequencyInSec);
        model.setUrl(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(model);
    }
}
