package net.minthe.ac.math;

public interface TrendLine {
    void setValues(double[] y, double[] x);
    double predict(double x);
}
