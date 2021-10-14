package com.ndrewcoding.greetingservice.controller;

import com.ndrewcoding.greetingservice.configuration.GreetingConfiguration;
import com.ndrewcoding.greetingservice.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "%s, %s";
    private final AtomicLong counter = new AtomicLong();

    private final GreetingConfiguration configuration;

    public GreetingController(GreetingConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        if (name.isEmpty()) name = configuration.getDefaultValue();

        return new Greeting(counter.incrementAndGet(), String.format(template, configuration.getGreeting(), name));
    }

}
