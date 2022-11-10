package info.memphis.point;

import info.memphis.line.Line;

public interface Point {

    Number x();

    Number y();

    Number z();

    double distanceTo(Point point);

    Line createLine(Point point);

}
