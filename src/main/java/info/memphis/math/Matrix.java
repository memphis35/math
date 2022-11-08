package info.memphis.math;

public interface Matrix {

    Matrix add(Matrix matrix);

    Matrix scalarMultiply(int multiplier);

    Matrix transpose();

    boolean isRowVector();
    boolean isColumnVector();
    boolean isSquareMatrix();

    boolean isTheSameSizeAs(Matrix matrix);

    int rows();
    int columns();

    Number element(int row, int column);
}
