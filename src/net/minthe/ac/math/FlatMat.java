package net.minthe.ac.math;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Created by Michael Kelley on 5/9/2017.
 */
public class FlatMat {
    public final double[] values;
    public final int rows;
    public final int cols;
    private double detMemo;
    private boolean detCalculated;

    private FlatMat(int rows, int cols) {
        detMemo = 0;
        detCalculated = false;
        this.rows = rows;
        this.cols = cols;
        values = new double[rows*cols];
    }

    public FlatMat(Double[][] vals) {
        this(vals.length, vals[0].length);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i*rows+j] = vals[i][j];
            }
        }
    }

    public FlatMat(double[][] vals) {
        this(vals.length, vals[0].length);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i*rows+j] = vals[i][j];
            }
        }
    }

    public FlatMat multiply(FlatMat b) {
        if (cols != b.rows) {
            System.out.println("Invalid matrix multiplication of " + this + "and" + b + ".");
            System.exit(2);
        }
        int new_rows = this.rows;
        int new_cols = b.cols;
        double[][] new_mat = new double[new_rows][new_cols];
        for (int i = 0; i < new_rows; i++) {
            for (int j = 0; j < new_cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    new_mat[i][j] += this.values[i*rows+k] * b.values[k*rows+j];
                }
            }
        }
        return new FlatMat(new_mat);
    }

    public FlatMat add(FlatMat b) {
        if (cols != b.cols || rows != b.rows) {
            System.out.println("Invalid matrix addition of " + this + "and" + b + ".");
            System.exit(2);
        }
        double[][] new_mat = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                new_mat[i][j] = this.values[i*rows+j] + b.values[i*rows+j];
            }
        }
        return new FlatMat(new_mat);
    }

    public double det() {
        if (detCalculated) return detMemo;
        if (rows == 2 && cols == 2) {
            return values[0*rows+0] * values[1*rows+1] - values[0*rows+1] * values[1*rows+0];
        }
        double sum = 0;
        double sign = 1;
        for (int i = 0; i < cols; i++) {
            if (this.values[0*rows+i] == 0) {
                sign *= -1;
                continue;
            }
            sum += sign * this.values[0*rows+i] * this.minor(0,i).det();
            sign *= -1;
        }
        this.detMemo = sum;
        detCalculated = true;
        return sum;
    }

    public boolean isSingular() {
        return this.det() == 0;
    }

    public FlatMat inverse() {
        double[][] new_mat = new double[rows][cols];
        double sign = 1;
        double invDet = 1/det();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                new_mat[j][i] = sign * invDet * this.minor(i, j).det();
                sign *= -1;
            }
        }
        return new FlatMat(new_mat);
    }

    public FlatMat minor(int r, int c) {
        double[][] new_mat = new double[rows - 1][cols - 1];
        int nr = 0;
        for (int i = 0; i < rows; i++) {
            int nc = 0;
            if (i == r) continue;
            for (int j = 0; j < cols; j++) {
                if (j == c) continue;
                new_mat[nr][nc++] = values[i*rows+j];
            }
            nr++;
        }
        return new FlatMat(new_mat);
    }

    public FlatMat negate() {
        return this.scalarMultiply(-1);
    }

    private static Random r = new Random();
    @NotNull
    public static FlatMat randLMult(FlatMat b, double min, double max) {
        int n_rows = 1;
        int n_cols = b.rows;
        return randFlatMat(n_rows, n_cols, min, max);
    }

    @NotNull
    public static FlatMat randRMult(FlatMat a, double min, double max) {
        int n_rows = a.cols;
        int n_cols = 1;
        return randFlatMat(n_rows, n_cols, min, max);
    }

    public boolean isUnary() {
        return rows == 1 && cols == 1;
    }

    @NotNull
    public static FlatMat randFlatMat(int n_rows, int n_cols, double min, double max) {
        double[][] vals = new double[n_rows][n_cols];
        double range = max - min;
        for (int i = 0; i < n_rows; i++) {
            for (int j = 0; j < n_cols; j++) {
                vals[i][j] = (r.nextDouble() * range) + min;
            }
        }
        return new FlatMat(vals);
    }

    @NotNull
    public FlatMat scalarMultiply(double a) {
        double[][] outvals = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                outvals[i][j] = a * values[i*rows+j];
            }
        }
        return new FlatMat(outvals);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlatMat)) {
            return false;
        }
        FlatMat other = (FlatMat) o;
        if (other.rows != rows || other.cols != cols) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (values[i*rows+j] != other.values[i*rows+j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += values[i*rows+j];
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < rows; i++) {
            sb.append('{');
            for (int j = 0; j < cols - 1; j++) {
                sb.append(values[i*rows+j]);
                sb.append(',');
            }
            sb.append(values[i*rows+cols - 1]);
            if (i == rows - 1) {
                sb.append("}");
            } else {
                sb.append("},\n");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
