package com.ijog.test.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clock")
public class TestCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(TestCallbackController.class);

    // TODO : Need a test application or jmeter setup, this currently is very rudimentary callback url that be used for a very basic test
    @PostMapping("/notification/test")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody final Notificaiton notificaiton) {
        logger.info(" ----------------------------------------------------------------------------------");
        logger.info(" ------------------------------- TEST CALLBACK -------------------------------------");
        logger.info(" ----------------------------------------------------------------------------------");
        logger.info(" ------------------------------------- {} ----------------------------------------", notificaiton.time);
        logger.info(" ----------------------------------------------------------------------------------");


        return ResponseEntity.noContent().header("Content-Length", "0").build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    static class Notificaiton {
        private String time;
    }
}
