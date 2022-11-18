package info.memphis.math;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ConcurrentIntegerMatrix extends IntegerMatrix {

    private ExecutorService service;

    public ConcurrentIntegerMatrix(long[][] matrix) {
        super(matrix);
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
        final Queue<Future<long[]>> newRows = new ConcurrentLinkedQueue<>();
        this.service = Executors.newFixedThreadPool(2);
        try {
            for (int r = 0; r < this.rows(); r++) {
                final Number[] thisRow = row(r);
                final Callable<long[]> callable = new Multiplication(thisRow, matrix);
                newRows.add(service.submit(callable));
            }
            while (!newRows.isEmpty()) {
                try {
                    builder.addIntegerRow(newRows.poll().get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            service.shutdown();
        }
        return builder.buildAsIntegerMatrix();
    }

    private record Multiplication(Number[] row, Matrix matrix) implements Callable<long[]> {

        @Override
            public long[] call() {
                final long[] newRow = new long[matrix.columns()];
                for (int c = 0; c < matrix.columns(); c++) {
                    final long sum = this.multiplyRowByColumn(this.row, matrix.column(c)).longValue();
                    newRow[c] = sum;
                }
                return newRow;
            }

            private Number multiplyRowByColumn(Number[] column, Number[] row) {
                return IntStream.range(0, column.length)
                        .mapToLong(i -> column[i].longValue() * row[i].longValue())
                        .sum();
            }
        }
}
