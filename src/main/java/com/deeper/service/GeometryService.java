package com.deeper.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.deeper.model.Line;
import com.deeper.model.Point;
import com.deeper.model.Square;
import com.deeper.util.GeometryUtils;

@Service
public class GeometryService {

    public boolean doesIntersect(Square square, Line line) {
        Assert.notNull(square, "Square must not be null");
        Assert.notNull(line, "Line must not be null");
        
        // Get the coordinates of the square
        double x1 = square.getX();
        double y1 = square.getY();
        double sideLength = square.getSideLength();
        double x2 = x1 + sideLength;
        double y2 = y1 + sideLength;

        // Define the four edges of the square as line segments
        Line[] squareEdges = {
            new Line(x1, y1, x2, y1), // Bottom edge
            new Line(x2, y1, x2, y2), // Right edge
            new Line(x2, y2, x1, y2), // Top edge
            new Line(x1, y2, x1, y1)  // Left edge
        };
    
        // Check if the input line intersects with any of the square's edges
        for (Line edge : squareEdges) {
            if (GeometryUtils.doLinesIntersect(line, edge)) {
                return true;
            }
        }
        
        return false;
    }

    public List<Point> findIntersectionPoint(Square square, Line line) {
        // Get the coordinates of the square
        double x1 = square.getX();
        double y1 = square.getY();
        double sideLength = square.getSideLength();
        double x2 = x1 + sideLength;
        double y2 = y1 + sideLength;
    
        // Define the four edges of the square as line segments
        Line[] squareEdges = {
            new Line(x1, y1, x2, y1), // Bottom edge
            new Line(x2, y1, x2, y2), // Right edge
            new Line(x2, y2, x1, y2), // Top edge
            new Line(x1, y2, x1, y1)  // Left edge
        };
    
        // Check if the input line intersects with any of the square's edges
        Set<Point> intersectionPoints = new HashSet<>();
        for (Line edge : squareEdges) {
            if (GeometryUtils.doLinesIntersect(line, edge)) {
                Point intersectionPoint = GeometryUtils.findIntersectionPoint(line, edge);
                // Check if the intersection point is within the square's bounds
                if (intersectionPoint.getX() >= x1 && intersectionPoint.getX() <= x2 &&
                    intersectionPoint.getY() >= y1 && intersectionPoint.getY() <= y2) {
                    intersectionPoints.add(intersectionPoint);
                }
            }
        }
    
        // If no intersection is found, return an empty list
        return new ArrayList<>(intersectionPoints);
    }
}
