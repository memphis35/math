package info.memphis.line;

import info.memphis.point.Point;
import info.memphis.point.RealPlanePoint;

public class StraightLine implements Line {
    private final Point first;
    private final Point second;
    public StraightLine(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double length() {
        return first.distanceTo(second);
    }

    @Override
    public Point midpoint() {
        final double x = (first.x().doubleValue() + second.x().doubleValue()) / 2;
        final double y = (first.y().doubleValue() + second.y().doubleValue()) / 2;
        return new RealPlanePoint(x, y);
    }
}
