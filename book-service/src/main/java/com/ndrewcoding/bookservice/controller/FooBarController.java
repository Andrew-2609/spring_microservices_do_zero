package com.ndrewcoding.bookservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @GetMapping("/foo-bar")
    @Bulkhead(name = "default", fallbackMethod = "fallbackMethod")
    public String fooBar() {
        logger.info("Request to foo-bar was received!");
        // ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
        return "Foo-Bar!!!";
    }

    public String fallbackMethod(Exception exception) {
        return "fallbackMethod: foo-bar!!!";
    }

}
