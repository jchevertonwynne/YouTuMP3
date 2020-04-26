package com.YouTuMP3.YouTuMP3.beans;

public class NewDefault {
    private String newDefault;
    
    public NewDefault() {}
    
    public String getNewDefault() {
        return newDefault;
    }

    public void setNewDefault(String newDefault) {
        this.newDefault = newDefault;
    }

    @Override
    public String toString() {
        return String.format("NewDefault(message=%s)", newDefault);
    }
}