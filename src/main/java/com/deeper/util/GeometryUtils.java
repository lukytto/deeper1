package com.deeper.util;

import com.deeper.model.Line;
import com.deeper.model.Point;

public class GeometryUtils {

    public static boolean doLinesIntersect(Line line1, Line line2) {
        double x1 = line1.getX1();
        double y1 = line1.getY1();
        double x2 = line1.getX2();
        double y2 = line1.getY2();

        double x3 = line2.getX1();
        double y3 = line2.getY1();
        double x4 = line2.getX2();
        double y4 = line2.getY2();

        double orientation1 = orientation(x1, y1, x2, y2, x3, y3);
        double orientation2 = orientation(x1, y1, x2, y2, x4, y4);
        double orientation3 = orientation(x3, y3, x4, y4, x1, y1);
        double orientation4 = orientation(x3, y3, x4, y4, x2, y2);

        if (orientation1 != orientation2 && orientation3 != orientation4) {
            return true;
        }

        if (orientation1 == 0 && isOnSegment(x1, y1, x3, y3, x2, y2)) return true;
        if (orientation2 == 0 && isOnSegment(x1, y1, x4, y4, x2, y2)) return true;
        if (orientation3 == 0 && isOnSegment(x3, y3, x1, y1, x4, y4)) return true;
        if (orientation4 == 0 && isOnSegment(x3, y3, x2, y2, x4, y4)) return true;

        return false;
    }

    public static Point findIntersectionPoint(Line line1, Line line2) {
        double x1 = line1.getX1();
        double y1 = line1.getY1();
        double x2 = line1.getX2();
        double y2 = line1.getY2();

        double x3 = line2.getX1();
        double y3 = line2.getY1();
        double x4 = line2.getX2();
        double y4 = line2.getY2();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denominator == 0) {
            // Lines are parallel, no intersection point
            return null;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            // Intersection point is within the line segments
            double intersectionX = x1 + t * (x2 - x1);
            double intersectionY = y1 + t * (y2 - y1);
            return new Point(intersectionX, intersectionY);
        } else {
            // Intersection point is outside the line segments
            return null;
        }
    }

    /**
     * Calculates the orientation of three points.
     */
    public static double orientation(double x1, double y1, double x2, double y2, double x3, double y3) {
        double val = (y2 - y1) * (x3 - x2) - (x2 - x1) * (y3 - y2);
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or counterclockwise
    }

    /**
     * Checks if a point lies on a line segment.
     */
    public static boolean isOnSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        return x2 <= Math.max(x1, x3) && x2 >= Math.min(x1, x3) &&
               y2 <= Math.max(y1, y3) && y2 >= Math.min(y1, y3);
    }

}
