package com.deeper.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.deeper.model.Line;
import com.deeper.model.Point;
import com.deeper.model.Square;
import com.deeper.service.GeometryService;

public class GeometryServiceTest {

    private final GeometryService geometryService = new GeometryService();

    @Test
    public void testDoesIntersect_LineIntersectsSquare() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(-1, 2, 6, 2);
        assertTrue(geometryService.doesIntersect(square, line));
    }

    @Test
    public void testDoesIntersect_LineDoesNotIntersectSquare() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(10, 10, 15, 15);
        assertFalse(geometryService.doesIntersect(square, line));
    }

    @Test
    public void testDoesIntersect_LineTouchesSquareEdge() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(0, 0, 5, 0);
        assertTrue(geometryService.doesIntersect(square, line));
    }

    @Test
    public void testDoesIntersect_LineIsCollinearWithSquareEdge() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(0, 0, 5, 0);
        assertTrue(geometryService.doesIntersect(square, line));
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquare() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(-1, 2, 6, 2);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(2, intersectionPoints.size());
        assertEquals(0, intersectionPoints.get(0).getX(), 0.01);
        assertEquals(2, intersectionPoints.get(0).getY(), 0.01);
        assertEquals(5, intersectionPoints.get(1).getX(), 0.01);
        assertEquals(2, intersectionPoints.get(1).getY(), 0.01);
    }

    @Test
    public void testFindIntersectionPoint_LineDoesNotIntersectSquare() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(10, 10, 15, 15);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertTrue(intersectionPoints.isEmpty());
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquareAtMultiplePoints() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(0, 0, 5, 5);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(2, intersectionPoints.size());
        List<Point> expectedPoints = Arrays.asList(new Point(0, 0), new Point(5, 5));
        assertIterableEquals(expectedPoints, intersectionPoints);
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquareAtTopLeftCorner() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(-1, -1, 1, 1);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(1, intersectionPoints.size());
        assertEquals(0, intersectionPoints.get(0).getX(), 0.01);
        assertEquals(0, intersectionPoints.get(0).getY(), 0.01);
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquareAtTopRightCorner() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(4, -1, 6, 1);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(1, intersectionPoints.size());
        assertEquals(5, intersectionPoints.get(0).getX(), 0.01);
        assertEquals(0, intersectionPoints.get(0).getY(), 0.01);
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquareAtBottomLeftCorner() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(-1, 4, 1, 6);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(1, intersectionPoints.size());
        assertEquals(0, intersectionPoints.get(0).getX(), 0.01);
        assertEquals(5, intersectionPoints.get(0).getY(), 0.01);
    }

    @Test
    public void testFindIntersectionPoint_LineIntersectsSquareAtBottomRightCorner() {
        Square square = new Square();
        square.setX(0);
        square.setY(0);
        square.setSideLength(5);

        Line line = new Line(4, 4, 6, 6);

        List<Point> intersectionPoints = geometryService.findIntersectionPoint(square, line);
        assertNotNull(intersectionPoints);
        assertEquals(1, intersectionPoints.size());
        assertEquals(5, intersectionPoints.get(0).getX(), 0.01);
        assertEquals(5, intersectionPoints.get(0).getY(), 0.01);
    }

}
