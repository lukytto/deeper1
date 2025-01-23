package com.deeper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.deeper.dto.IntersectionDto;
import com.deeper.dto.RequestCountResponse;
import com.deeper.model.Point;
import com.deeper.service.GeometryService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class GeometryController {

    private static final Logger logger = LoggerFactory.getLogger(GeometryController.class);

    private final GeometryService geometryService;
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private final AtomicInteger activeRequests = new AtomicInteger(0);

    @Autowired
    public GeometryController(GeometryService geometryService) {
        this.geometryService = geometryService;
    }

    @PostMapping("/intersect")
    public ResponseEntity<String> checkIntersection(@RequestBody IntersectionDto request) {
        boolean intersects = geometryService.doesIntersect(request.getSquare(), request.getLine());
        List<Point> intersectionPoints = geometryService.findIntersectionPoint(request.getSquare(), request.getLine());
        
        if (intersects) {
            StringBuilder intersectionPointsString = new StringBuilder();
            for (Point point : intersectionPoints) {
                intersectionPointsString.append("(").append(point.getX()).append(", ").append(point.getY()).append(") ");
            }
            return ResponseEntity.ok("Intersect: true, Intersection points: " + intersectionPointsString.toString().trim());
        } else {
            return ResponseEntity.ok("Intersect: false");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/request-count")
    public ResponseEntity<RequestCountResponse> getRequestCount() {
        int total = requestCount.get();
        int active = activeRequests.get();
        RequestCountResponse response = new RequestCountResponse(total, active);
        logger.info("Request count retrieved. Total: {}, Active: {}", total, active);
        return ResponseEntity.ok(response);
    }

    public void incrementRequestCount() {
        requestCount.incrementAndGet();
        logger.info("Request count incremented. Total: {}", requestCount.get());
    }

    public void incrementActiveRequests() {
        activeRequests.incrementAndGet();
        logger.info("Active requests incremented. Active: {}", activeRequests.get());
    }

    public void decrementActiveRequests() {
        activeRequests.decrementAndGet();
        logger.info("Active requests decremented. Active: {}", activeRequests.get());
    }

    public void resetRequestCount() {
        requestCount.set(0);
        activeRequests.set(0);
        logger.info("Request count reset. Total: {}, Active: {}", requestCount.get(), activeRequests.get());
    }

    public int getActiveRequestsInt() {
        return activeRequests.get();
    }

    public int getRequestCountInt() {
        return requestCount.get();
    }

}
