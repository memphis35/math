package info.memphis.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MatrixBuilder {

    private final List<long[]> integers = new LinkedList<>();
    private final List<double[]> floats = new LinkedList<>();

    public MatrixBuilder addIntegerRow(long ... values) {
        Objects.requireNonNull(values, "Row values should not be null");
        this.integers.add(values);
        return this;
    }

    public MatrixBuilder addFloatRow(double ... values) {
        Objects.requireNonNull(values, "Row values should not be null");
        this.floats.add(values);
        return this;
    }

    public Matrix buildAsIntegerMatrix() {
        final long[][] matrix = this.integers.toArray(long[][]::new);
        return new IntegerMatrix(matrix);
    }

    public Matrix buildAsFloatMatrix() {
        final double[][] matrix = IntStream.range(0, this.floats.size() - 1)
                .mapToObj(this.floats::get)
                .toArray(double[][]::new);
        //TODO create Float-based Matrix implementation
        return null;
    }
}
