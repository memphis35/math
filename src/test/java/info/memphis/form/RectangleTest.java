package info.memphis.form;

import info.memphis.point.IntegerPlanePoint;
import info.memphis.point.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RectangleTest {

    @ParameterizedTest
    @CsvSource("2, 5, 10.0, 15, 15, 225.0, 42, 17, 714.0")
    void whenCalculateArea_thenSuccess(long length, long width, double expected) {
        // Given
        final Point center = new IntegerPlanePoint(0, 0);
        final Form rectangle = new Rectangle(center, length, width);

        // When
        final double actual = rectangle.area();

        // Then
        Assertions.assertEquals(expected, actual, 0.0001);
    }
}