package info.memphis.line;

import info.memphis.point.IntegerPlanePoint;
import info.memphis.point.Point;
import info.memphis.point.RealPlanePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StraightLineTest {

    @ParameterizedTest
    @CsvSource(value = "2, 5, 26.4420, 15, 9, 26.67808, 17, 1, 34.58439")
    void whenGetLength_thenSuccess(long x, long y, double expected) {
        // Given
        final Point first = new RealPlanePoint(0.51, 31.4);
        final Point second = new IntegerPlanePoint(x, y);

        // When
        final double actual = new StraightLine(first, second).length();

        //Then
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @ParameterizedTest
    @CsvSource(value = "0, 0, 0.255, 15.7, 5, 13, 2.755, 22.2, 42, 17, 21.255, 24.2")
    void whenCalculateMidpoint_thenSuccess(long x, long y, double expX, double expY) {
        // Given
        final Point first = new RealPlanePoint(0.51, 31.4);
        final Point second = new IntegerPlanePoint(x, y);
        final Point expected = new RealPlanePoint(expX, expY);

        // When
        final Point actual = new StraightLine(first, second).midpoint();

        //Then
        Assertions.assertEquals(expected, actual);
    }
}