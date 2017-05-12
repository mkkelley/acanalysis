package net.minthe.ac.math;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class LinearRegression {
    public static OLSMultipleLinearRegression polyReg(double[] x, double[] y, int degree) {
        PolyTrendLine t = new PolyTrendLine(degree);
        t.setValues(y, x);
        return t.model;
    }
}
