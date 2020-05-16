package com.YouTuMP3.YouTuMP3.beans.output;

public class Count {
    private int timesCalled;
    private String message;

    public Count(int timesCalled, String message) {
        this.timesCalled = timesCalled;
        this.message = message;
    }
    
    public int getTimesCalled() {
        return this.timesCalled;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setTimesCalled(int timesCalled) {
        this.timesCalled = timesCalled;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Count(count=%d, message=%s)", 
            this.timesCalled, 
            this.message
        );
    }
}