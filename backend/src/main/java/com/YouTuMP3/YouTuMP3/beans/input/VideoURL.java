package com.YouTuMP3.YouTuMP3.beans.input;


public class VideoURL {
    private String url;

    public VideoURL() {}
    
    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("VideoURL(url=%s)", url);
    }
}