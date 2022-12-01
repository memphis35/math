package info.memphis.math;

public interface IntegerMatrix extends Matrix {


    long[] row(int index);

    long[] column(int index);

    long element(int row, int column);
}
