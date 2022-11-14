package info.memphis.form;

import info.memphis.point.Point;
import info.memphis.point.RealPlanePoint;

import java.util.Objects;

public class Rectangle implements Form {

    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;

    public Rectangle(Point center, long lengthX, long lengthY) {
        Objects.requireNonNull(center, "Center point should not be empty");
        if (lengthX <= 0 || lengthY <= 0) {
            throw new IllegalArgumentException("Side length should be a positive number");
        }
        final double halfX = lengthX / 2.0;
        final double halfY = lengthY / 2.0;
        a = new RealPlanePoint(center.x().doubleValue() - halfX, center.y().doubleValue() + halfY);
        b = new RealPlanePoint(center.x().doubleValue() + halfX, center.y().doubleValue() + halfY);
        c = new RealPlanePoint(center.x().doubleValue() + halfX, center.y().doubleValue() - halfY);
        d = new RealPlanePoint(center.x().doubleValue() - halfX, center.y().doubleValue() - halfY);
    }

    @Override
    public double area() {
        return a.distanceTo(b) * b.distanceTo(c);
    }
}
