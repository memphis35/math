package info.memphis.math;

import info.memphis.exception.IncorrectMatrixSizeException;
import info.memphis.exception.WrongOperationExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class IntegerMatrixTest {

    @Test
    void givenNullValue_whenCreatingMatrix_thenFailed() {
        // When
        final Executable executable = () -> new IntegerMatrix(null);

        // Then
        final NullPointerException exception = Assertions.assertThrows(NullPointerException.class, executable);
        Assertions.assertEquals("Matrix value should not be null", exception.getMessage());
    }

    @Test
    void givenRowNullValue_whenCreatingMatrix_thenFailed() {
        // When
        final Executable executable = () -> new IntegerMatrix(new long[][]{{1, 2, 3}, null});

        // Then
        final NullPointerException exception = Assertions.assertThrows(NullPointerException.class, executable);
        Assertions.assertEquals("Matrix row should not be null", exception.getMessage());
    }

    @Test
    void givenEmptyArrayValue_whenCreatingMatrix_thenFailed() {
        // When
        final Executable executable = () -> new IntegerMatrix(new long[0][42]);

        // Then
        final String expectedMsg = "Matrix should have at least one row";
        Assertions.assertThrows(IncorrectMatrixSizeException.class, executable, expectedMsg);
    }

    @Test
    void givenEmptyRowValue_whenCreatingMatrix_thenFailed() {
        // When
        final Executable executable = () -> new IntegerMatrix(new long[][]{{}, {}});

        // Then
        final String expectedMsg = "All rows should have the same length and contain at least one element";
        Assertions.assertThrows(IncorrectMatrixSizeException.class, executable, expectedMsg);
    }

    @Test
    void givenRowsWithDifferentLengths_whenCreatingMatrix_thenFailed() {
        // When
        final Executable executable = () -> new IntegerMatrix(new long[][]{{1, 2, 3}, {4}, {5, 6, 7}});

        // Then
        final String expectedMsg = "All rows should have the same length and contain at least one element";
        final IncorrectMatrixSizeException exception =
                Assertions.assertThrows(IncorrectMatrixSizeException.class, executable, expectedMsg);
        Assertions.assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void givenTheSameSizedMatrices_whenAdd_thenSuccess() {
        // Given
        final Matrix first = new IntegerMatrix(new long[][]{{1, 2, 3}, {4, 5, 6}});
        final Matrix second = new IntegerMatrix(new long[][]{{7, 8, 9}, {10, 11, 12}});
        final Matrix expected = new IntegerMatrix(new long[][]{{8, 10, 12}, {14, 16, 18}});

        // When
        final Matrix actual = first.add(second);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenDifferentSizedMatrices_whenAdd_thenSuccess() {
        // Given
        final Matrix first = new IntegerMatrix(new long[][]{{1, 2, 3, 4}, {4, 5, 6, 8}});
        final Matrix second = new IntegerMatrix(new long[][]{{7, 8, 9}, {10, 11, 12}});

        // When
        final Executable executable = () -> first.add(second);

        //Then
        final WrongOperationExeption exception = Assertions.assertThrows(WrongOperationExeption.class, executable);
        Assertions.assertEquals("Can't add matrices because of different sizes", exception.getMessage());
    }

    @Test
    void whenScalarMultiply_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2, 3, 4}, {5, 6, 7, 8}});
        final Matrix expected = new IntegerMatrix(new long[][]{{3, 6, 9, 12}, {15, 18, 21, 24}});

        // When
        final Matrix actual = matrix.scalarMultiply(3);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenRectangularMatrix_whenTranspose_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}});
        final Matrix expected = new IntegerMatrix(new long[][]{{1, 5, 9}, {2, 6, 10}, {3, 7, 11}, {4, 8, 12}});

        // When
        final Matrix actual = matrix.transpose();

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(4, actual.rows());
        Assertions.assertEquals(3, actual.columns());
    }

    @Test
    void givenRowVectorMatrix_whenCallIsRowVector_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2, 3, 4, 5}});

        // When
        final boolean actual = matrix.isRowVector();

        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void givenNonRowVectorMatrix_whenCallIsRowVector_thenFailed() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2}, {6, 7}});

        // When
        final boolean actual = matrix.isRowVector();

        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void givenColumnVectorMatrix_whenCallIsColumnVector_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1}, {2}, {3}, {4}});

        // When
        final boolean actual = matrix.isColumnVector();

        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void givenNonColumnVectorMatrix_whenCallIsColumnVector_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}});

        // When
        final boolean actual = matrix.isColumnVector();

        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void givenSquareMatrix_whenCallIsSquareMatrix_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});

        // When
        final boolean actual = matrix.isSquareMatrix();

        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void givenNonSquareMatrix_whenCallIsSquareMatrix_thenFailed() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2}, {4, 5}, {7, 8}});

        // When
        final boolean actual = matrix.isSquareMatrix();

        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void givenTheSameSizedMatrices_whenCallIsTheSameSizeAs_thenSuccess() {
        // Given
        final Matrix first = new IntegerMatrix(new long[][]{{1, 2}, {3, 4}, {5, 6}});
        final Matrix second = new IntegerMatrix(new long[][]{{42, 54}, {43, 32}, {35, 13}});

        // When
        final boolean actual = first.isTheSameSizeAs(second);

        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void givenDifferentSizedMatrices_whenCallIsTheSameSizeAs_thenFailed() {
        // Given
        final Matrix first = new IntegerMatrix(new long[][]{{1, 2}, {3, 4}, {5, 6}});
        final Matrix second = new IntegerMatrix(new long[][]{{42, 54}, {43, 32}, {35, 13}});

        // When
        final boolean actual = first.isTheSameSizeAs(second);

        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void whenCallRows_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2}, {3, 4}, {5, 6}});

        // When
        final long actual = matrix.rows();

        // Then
        Assertions.assertEquals(3, actual);
    }

    @Test
    void whenCallColumns_thenSuccess() {
        // Given
        final Matrix matrix = new IntegerMatrix(new long[][]{{1, 2, 3, 4}, {3, 4, 5, 6}, {5, 6, 7, 8}});

        // When
        final long actual = matrix.columns();

        // Then
        Assertions.assertEquals(4, actual);
    }

    @Test
    void whenMultiplyMatrices_thenSuccess() {
        // Given
        final Matrix first = new IntegerMatrix(new long[][]{{2, 3, 4}, {1, 0, 0}});
        final Matrix second = new IntegerMatrix(new long[][]{{0, 1000}, {1, 100}, {0, 10}});
        final Matrix expected = new IntegerMatrix(new long[][]{{3, 2340}, {0, 1000}});

        // When
        final Matrix actual = first.multiply(second);

        // Then
        Assertions.assertEquals(expected, actual);
    }
}