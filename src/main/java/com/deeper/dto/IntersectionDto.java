package com.deeper.dto;

import com.deeper.model.Line;
import com.deeper.model.Square;

public class IntersectionDto {
    private Square square;
    private Line line;
    
    public Square getSquare() {
        return square;
    }
    public void setSquare(Square square) {
        this.square = square;
    }
    public Line getLine() {
        return line;
    }
    public void setLine(Line line) {
        this.line = line;
    }

    
}
