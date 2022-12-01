package info.memphis.math;

public class RealMatrixImpl implements RealMatrix {

    private final double[][] matrix;
    public RealMatrixImpl(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public Matrix add(Matrix matrix) {
        return null;
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        return null;
    }

    @Override
    public Matrix scalarMultiply(int multiplier) {
        return null;
    }

    @Override
    public Matrix transpose() {
        return null;
    }

    @Override
    public boolean isRowVector() {
        return false;
    }

    @Override
    public boolean isColumnVector() {
        return false;
    }

    @Override
    public boolean isSquareMatrix() {
        return false;
    }

    @Override
    public boolean isTheSameSizeAs(Matrix matrix) {
        return false;
    }

    @Override
    public int rows() {
        return 0;
    }

    @Override
    public int columns() {
        return 0;
    }

    @Override
    public double element(int row, int column) {
        return 0;
    }
}
