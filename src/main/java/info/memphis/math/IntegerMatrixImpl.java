package info.memphis.math;

import info.memphis.exception.IncorrectMatrixSizeException;
import info.memphis.exception.WrongOperationExeption;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntegerMatrixImpl implements IntegerMatrix {

    private final long[][] matrix;

    public IntegerMatrixImpl(long[][] matrix) {
        Objects.requireNonNull(matrix, "Matrix value should not be null");
        if (matrix.length == 0) {
            throw new IncorrectMatrixSizeException("Matrix should have at least one row");
        }
        Stream.of(matrix).forEach(row -> Objects.requireNonNull(row, "Matrix row should not be null"));
        final int firstRowLength = matrix[0].length;
        final boolean allRowsHaveTheSameLength = Stream.of(matrix).allMatch(row -> row.length == firstRowLength);
        if (firstRowLength == 0 || !allRowsHaveTheSameLength) {
            throw new IncorrectMatrixSizeException("All rows should have the same length and contain at least one element");
        }
        this.matrix = matrix;
    }

    @Override
    public Matrix add(Matrix matrix) {
        if (!this.isTheSameSizeAs(matrix)) {
            throw new WrongOperationExeption("Can't add matrices because of different sizes");
        } else if (matrix instanceof IntegerMatrix) {
            return addIntegerMatrix((IntegerMatrix) matrix);
        } else {
            return this.addRealMatrix((RealMatrix) matrix);
        }
    }

    private Matrix addIntegerMatrix(IntegerMatrix matrix) {
        final long[][] result = new long[this.rows()][this.columns()];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                result[i][c] = this.matrix[i][c] + matrix.element(i, c);
            }
        }
        return new IntegerMatrixImpl(result);
    }

    private Matrix addRealMatrix(RealMatrix matrix) {
        final double[][] result = new double[this.rows()][this.columns()];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                result[i][c] = this.matrix[i][c] + matrix.element(i, c);
            }
        }
        return new RealMatrixImpl(result);
    }

    @Override
    public Matrix scalarMultiply(int multiplier) {
        final long[][] result = new long[this.rows()][this.columns()];
        for (int r = 0; r < this.rows(); r++) {
            for (int c = 0; c < this.columns(); c++) {
                result[r][c] = this.matrix[r][c] * multiplier;
            }
        }
        return new IntegerMatrixImpl(result);
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        final IntegerMatrix mx = (IntegerMatrix) matrix;
        if (this.columns() != matrix.rows()) {
            final String msg = String.format(
                    "Matrices are not met the conditions. Matrix A columns: %d, matrix B rows: %d",
                    this.columns(), matrix.rows());
            throw new ArithmeticException(msg);
        }
        final MatrixBuilder builder = new MatrixBuilder();
        for (int r = 0; r < this.rows(); r++) {
            final long[] thisRow = this.matrix[r];
            final long[] newRow = new long[matrix.columns()];
            for (int c = 0; c < matrix.columns(); c++) {
                final long sum = this.multiplyRowByColumn(thisRow, mx.column(c)).longValue();
                newRow[c] = sum;
            }
            builder.addIntegerRow(newRow);
        }
        return builder.buildAsIntegerMatrix();
    }

    private Number multiplyRowByColumn(long[] column, long[] row) {
        return IntStream.range(0, column.length)
                .mapToLong(i -> column[i] * row[i])
                .sum();
    }

    @Override
    public Matrix transpose() {
        final int futureColumns = this.rows();
        final int futureRows = this.columns();
        final long[][] transposed = new long[futureRows][futureColumns];
        for (int i = 0; i < futureRows; i++) {
            final long[] row = new long[futureColumns];
            for (int j = 0; j < row.length; j++) {
                row[j] = matrix[j][i];
            }
            transposed[i] = row;
        }
        return new IntegerMatrixImpl(transposed);
    }

    @Override
    public boolean isRowVector() {
        return this.matrix.length == 1;
    }

    @Override
    public boolean isColumnVector() {
        return this.matrix[0].length == 1;
    }

    @Override
    public boolean isSquareMatrix() {
        return this.matrix.length == this.matrix[0].length;
    }

    @Override
    public boolean isTheSameSizeAs(Matrix matrix) {
        return this.rows() == matrix.rows() && this.columns() == matrix.columns();
    }

    @Override
    public int rows() {
        return this.matrix.length;
    }

    @Override
    public int columns() {
        return this.matrix[0].length;
    }

    @Override
    public long[] row(int index) {
        return Arrays.copyOf(this.matrix[index], this.matrix[index].length);
    }

    @Override
    public long[] column(int index) {
        return Arrays.stream(this.matrix)
                .mapToLong(arr -> arr[index])
                .toArray();
    }

    @Override
    public long element(int row, int column) {
        return this.matrix[row][column];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IntegerMatrixImpl that = (IntegerMatrixImpl) o;
        if (!this.isTheSameSizeAs(that)) {
            return false;
        } else {
            return IntStream.range(0, this.matrix.length)
                    .allMatch(index -> {
                        final long[] thisRow = this.matrix[index];
                        final long[] thatRow = that.matrix[index];
                        return IntStream.range(0, thisRow.length).allMatch(i -> thisRow[i] == thatRow[i]);
                    });
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) matrix);
    }

    @Override
    public String toString() {
        return Stream.of(this.matrix)
                .map(Arrays::toString)
                .collect(Collectors.joining(",\n"));
    }
}
