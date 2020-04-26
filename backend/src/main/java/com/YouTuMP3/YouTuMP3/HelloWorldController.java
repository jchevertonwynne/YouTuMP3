package com.YouTuMP3.YouTuMP3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(path="/")
    public String helloWorld() {
        return "HelloWorld";
    }
}
