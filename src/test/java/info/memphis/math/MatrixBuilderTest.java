package info.memphis.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixBuilderTest {

    @Test
    void buildAsIntegerMatrix() {
        // Given
        final Matrix expected = new IntegerMatrix(new long[][]{{1, 2}, {3, 4}, {5, 6}});
        MatrixBuilder builder = new MatrixBuilder();

        // When
        final Matrix actual = builder
                .addIntegerRow(1, 2)
                .addIntegerRow(3, 4)
                .addIntegerRow(5, 6)
                .buildAsIntegerMatrix();

        // Then
        Assertions.assertEquals(expected, actual);
    }
}