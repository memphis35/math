package info.memphis.point;

import info.memphis.line.Line;

public class PlaneIntegerPoint implements Point {

    private final long x;
    private final long y;

    public PlaneIntegerPoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Number x() {
        return this.x;
    }

    @Override
    public Number y() {
        return this.y;
    }

    @Override
    public Number z() {
        return 0;
    }

    @Override
    public double distanceTo(Point point) {
        final double squareX = Math.pow(this.x - point.x().doubleValue(), 2);
        final double squareY = Math.pow(this.y - point.y().doubleValue(), 2);
        final double squareZ = Math.pow(point.z().doubleValue(), 2);
        return Math.sqrt(squareX + squareY + squareZ);
    }

    @Override
    public Line createLine(Point point) {
        return new StraightLine(this, point);
    }
}
