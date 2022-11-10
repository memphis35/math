package info.memphis.point;

import info.memphis.line.Line;

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
}
