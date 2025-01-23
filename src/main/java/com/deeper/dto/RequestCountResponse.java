package com.deeper.dto;

public class RequestCountResponse {
    private final int totalRequests;
    private final int activeRequests;

    public RequestCountResponse(int totalRequests, int activeRequests) {
        this.totalRequests = totalRequests;
        this.activeRequests = activeRequests;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public int getActiveRequests() {
        return activeRequests;
    }
}