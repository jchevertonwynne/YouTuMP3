package com.YouTuMP3.YouTuMP3.beans.output;

public class RawVideoURL {
    private String rawURL;

    public RawVideoURL(String rawUrl) {
        this.rawURL = rawUrl;
    }
    
    public String getURL() {
        return rawURL;
    }

    public void setURL(String url) {
        this.rawURL = url;
    }

    @Override
    public String toString() {
        return String.format("RawVideoURL(url=%s)", rawURL);
    }
}