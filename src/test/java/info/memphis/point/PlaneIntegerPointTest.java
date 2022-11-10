package info.memphis.point;

import info.memphis.line.Line;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlaneIntegerPointTest {

    private final double testDelta = 0.0001;

    @ParameterizedTest
    @CsvSource(value = "42, 17, 39.4588, 13, 32, 23.2594, 156, 212, 252.6064")
    void givenTwoPlanePoints_whenCalculateDistance_thenSuccess(long x, long y, double expected) {
        // Given
        final Point first = new PlaneIntegerPoint(3, 11);
        final Point second = new PlaneIntegerPoint(x, y);

        // When
        final double actual = first.distanceTo(second);

        // Then
        Assertions.assertEquals(expected, actual, testDelta);
    }

    @Test
    void givenTwoPlanePoints_whenCreateLine_thenSuccess() {
        // Given
        final Point first = new PlaneIntegerPoint(1, 15);
        final Point second = new PlaneIntegerPoint(3, 8);

        // When
        final Line actual = first.createLine(second);

        // Then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(7.2801, actual.length(), testDelta);
    }
}