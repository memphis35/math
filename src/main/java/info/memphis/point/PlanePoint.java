package info.memphis.point;

import info.memphis.line.Line;
import info.memphis.line.StraightLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class PlanePoint implements Point {
    
    private static final int scale = 3;
    private static final RoundingMode mode = RoundingMode.DOWN;

    private final Number x;
    private final Number y;

    protected PlanePoint(Number x, Number y) {
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
        final double squareX = Math.pow(this.x.doubleValue() - point.x().doubleValue(), 2);
        final double squareY = Math.pow(this.y.doubleValue() - point.y().doubleValue(), 2);
        final double squareZ = Math.pow(point.z().doubleValue(), 2);
        return Math.sqrt(squareX + squareY + squareZ);
    }

    @Override
    public Line createLine(Point point) {
        return new StraightLine(this, point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point that)) return false;
        final BigDecimal thisFirst = BigDecimal.valueOf(x.doubleValue()).setScale(scale, mode);
        final BigDecimal thisSecond = BigDecimal.valueOf(y.doubleValue()).setScale(scale, mode);
        final BigDecimal thatFirst = BigDecimal.valueOf(that.x().doubleValue()).setScale(scale, mode);
        final BigDecimal thatSecond = BigDecimal.valueOf(that.y().doubleValue()).setScale(scale, mode);
        return thisFirst.equals(thatFirst) && thisSecond.equals(thatSecond);
    }

    @Override
    public int hashCode() {
        final BigDecimal thisX = BigDecimal.valueOf(x.doubleValue()).setScale(scale, mode);
        final BigDecimal thisY = BigDecimal.valueOf(y.doubleValue()).setScale(scale, mode);
        return Objects.hash(thisX, thisY);
    }
}
