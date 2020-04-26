package com.YouTuMP3.YouTuMP3;

import com.YouTuMP3.YouTuMP3.beans.Count;
import com.YouTuMP3.YouTuMP3.beans.NewDefault;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YouTuMP3Controller {
    private int count = 0;
    private String defaultMessage = "Default Message";

    @GetMapping(path="/")
    public String helloWorld() {
        return "YouTuMP3";
    }

    @GetMapping(path="/count")
    public Count count() {
        return new Count(count++, defaultMessage);
    }

    @GetMapping(path="/count/{message}")
    public Count countWithCustomMessage(@PathVariable String message) {
        return new Count(count++, message);
    }

    @GetMapping(path="/count/{message}/{part2}")
    public Count countWithCustomMessages(@PathVariable String message, @PathVariable String part2) {
        return new Count(count++, part2 + message);
    }

    @PostMapping(path="/message", consumes = "application/json")
    public void changeMessage(@RequestBody NewDefault newDefault) {
        defaultMessage = newDefault.getNewDefault();
    }
}
