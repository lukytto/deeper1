package com.deeper.main.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deeper.controller.GeometryController;
import com.deeper.dto.IntersectionDto;
import com.deeper.model.Line;
import com.deeper.model.Square;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GeometryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GeometryController geometryController;

    @Test
    public void testCheckIntersection_LineIntersectsSquare() throws Exception {
        IntersectionDto request = new IntersectionDto();
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);
        request.setSquare(square);

        Line line = new Line(-1, 2, 6, 2);
        request.setLine(line);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/intersect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Intersect: true, Intersection points: (0.0, 2.0) (5.0, 2.0)"));
    }

    @Test
    public void testCheckIntersection_LineDoesNotIntersectSquare() throws Exception {
        IntersectionDto request = new IntersectionDto();
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);
        request.setSquare(square);

        Line line = new Line(10, 10, 15, 15);
        request.setLine(line);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/intersect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Intersect: false"));
    }

    @Test
    public void testGetRequestCount_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/request-count"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testResetRequestCount() {
        geometryController.resetRequestCount();
        assertEquals(0, geometryController.getRequestCountInt());
        assertEquals(0, geometryController.getActiveRequestsInt());
    }

    @Test
    @WithMockUser (username = "user", password = "password", roles = {"USER"})
    public void testMultipleRequests() throws Exception {
        geometryController.resetRequestCount();
        assertEquals(0, geometryController.getRequestCountInt());
    
        // Simulate multiple requests
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 4; i++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/api/intersect")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"square\":{\"x\":0,\"y\":0,\"sideLength\":5},\"line\":{\"x1\":-1,\"y1\":2,\"x2\":6,\"y2\":2}}"))
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    
        // Wait for all requests to complete
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    
        // Call the /request-count endpoint to verify the counts
        mockMvc.perform(MockMvcRequestBuilders.get("/api/request-count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRequests").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activeRequests").value(1));
    }

    @Test
    @WithMockUser (username = "user", password = "password", roles = {"USER"})
    public void testGetRequestCount() throws Exception {
        geometryController.resetRequestCount();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/intersect")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"square\":{\"x\":0,\"y\":0,\"sideLength\":5},\"line\":{\"x1\":-1,\"y1\":2,\"x2\":6,\"y2\":2}}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/request-count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRequests").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activeRequests").value(1));
    }

}
