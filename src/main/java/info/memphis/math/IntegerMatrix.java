package info.memphis.math;

import info.memphis.exception.IncorrectMatrixSizeException;
import info.memphis.exception.WrongOperationExeption;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class IntegerMatrix implements Matrix {

    private final long[][] matrix;

    public IntegerMatrix(long[][] matrix) {
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
        }
        final long[][] result = new long[this.rows()][this.columns()];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                result[i][c] = this.matrix[i][c] + matrix.element(i, c).intValue();
            }
        }
        return new IntegerMatrix(result);
    }

    @Override
    public Matrix scalarMultiply(int multiplier) {
        final long[][] result = new long[this.rows()][this.columns()];
        for (int r = 0; r < this.rows(); r++) {
            for (int c = 0; c < this.columns(); c++) {
                result[r][c] = this.matrix[r][c] * multiplier;
            }
        }
        return new IntegerMatrix(result);
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        if (this.columns() != matrix.rows()) {
            final String msg = String.format(
                    "Matrices are not met the conditions. Matrix A columns: %d, matrix B rows: %d",
                    this.columns(), matrix.rows());
            throw new ArithmeticException(msg);
        }
        final MatrixBuilder builder = new MatrixBuilder();
        for (int r = 0; r < this.rows(); r++) {
            final Number[] thisRow = row(r);
            final long[] newRow = new long[matrix.columns()];
            for (int c = 0; c < matrix.columns(); c++) {
                final long sum = this.multiplyRowByColumn(thisRow, matrix.column(c)).longValue();
                newRow[c] = sum;
            }
            builder.addIntegerRow(newRow);
        }
        return builder.buildAsIntegerMatrix();
    }

    private Number multiplyRowByColumn(Number[] column, Number[] row) {
        return IntStream.range(0, column.length)
                .mapToLong(i -> column[i].longValue() * row[i].longValue())
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
        return new IntegerMatrix(transposed);
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
    public Number[] row(int index) {
        return LongStream.of(this.matrix[index])
                .boxed()
                .toArray(Number[]::new);
    }

    @Override
    public Number[] column(int index) {
        return Arrays.stream(this.matrix)
                .map(arr -> arr[index])
                .toArray(Number[]::new);
    }

    @Override
    public Number element(int row, int column) {
        return this.matrix[row][column];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IntegerMatrix that = (IntegerMatrix) o;
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

    public static void main(String[] args) {
        Random random = new Random();
        MatrixBuilder builder = new MatrixBuilder();
        IntStream.range(0, 2500).forEach(index -> {
            long[] row = IntStream.range(0, 2500)
                    .mapToLong(i -> random.nextLong(-100_000, 100_000))
                    .toArray();
            builder.addIntegerRow(row);
        });
        Matrix first = builder.buildAsConcurrentIntegerMatrix();
        Matrix second = builder.buildAsConcurrentIntegerMatrix();
        System.out.println("Start");
        final long start = System.currentTimeMillis();
        Matrix result = first.multiply(second);
        final long end = System.currentTimeMillis();
        System.out.println(result.element(0, 0));
        System.out.println(result.element(100, 100));
        System.out.println(Duration.of(end - start, ChronoUnit.MILLIS).getSeconds() + " seconds");
    }
}
