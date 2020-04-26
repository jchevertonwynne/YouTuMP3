package com.YouTuMP3.YouTuMP3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloWorldController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Response(counter.incrementAndGet(), String.format(template, name));
    }
}
