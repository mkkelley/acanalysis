package net.minthe.ac.wm;

import net.minthe.ac.jen.AlienReaderSM;
import net.minthe.ac.math.LinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.ejml.simple.SimpleMatrix;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Michael Kelley on 5/10/2017.
 */
public class DoubleArrayEvaluator implements FitnessEvaluator<double[]> {
    private static final int N_ROWS = 20;
    private static final int N_COLS = 20;

    static HashMap<Integer, SimpleMatrix> testValues;
    static double bestr2 = 0;
    static double[] best_ys;

    static {
        AlienReaderSM ar = new AlienReaderSM("C:\\Users\\purti\\ClionProjects\\aliencounter\\numbers.txt");
        testValues = ar.values;
    }
    public double getFitness(double[] in, List<? extends double[]> population) {
        double[][] lMult_vals = new double[1][N_ROWS];
        double[][] v_1_vals = new double[N_ROWS][N_COLS];
        double[][] v_2_vals = new double[N_ROWS][N_COLS];
        double[][] rMult_vals = new double[N_COLS][1];
        int idx = 0;
        for (int i = 0; i < N_ROWS; i++) {
            lMult_vals[0][i] = in[idx++];
        }
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                v_1_vals[i][j] = in[idx];
                v_2_vals[i][j] = in[idx + N_ROWS * N_COLS];
                idx++;
            }
        }
        idx += N_ROWS * N_COLS;
        for (int i = 0; i < N_COLS; i++) {
            rMult_vals[i][0] = in[idx++];
        }
        SimpleMatrix lMult = new SimpleMatrix(lMult_vals);
        SimpleMatrix v_1 = new SimpleMatrix(v_1_vals);
        SimpleMatrix v_2 = new SimpleMatrix(v_2_vals);
        SimpleMatrix rMult = new SimpleMatrix(rMult_vals);

        double[] xs = new double[testValues.size()];
        double[] ys = new double[testValues.size()];
        for (int i = 1; i <= testValues.size(); i++) {
            SimpleMatrix m = testValues.get(i);
            m = lMult.mult(m.plus(v_1).mult(v_2)).mult(rMult);
//            if (!m.isUnary()) System.exit(42);
            xs[i - 1] = i;
            ys[i - 1] = m.get(0,0);
        }
        double sum = 0;
        for (int i = 1; i < testValues.size(); i++) {
            if (ys[i] > ys[i-1]) sum++;
        }
        OLSMultipleLinearRegression model = LinearRegression.polyReg(xs, ys, 1);
        double r2 = model.calculateRSquared();
//        if (r2 > bestr2) {
//            bestr2 = r2;
//            best_ys = ys;
//        }
        if (Double.isNaN(r2)) return 0;
//        if (sum * r2 == NaN) return 0;
        return sum * r2;
//        return model.calculateRSquared() * SimpleMatrixh.min(1, SimpleMatrixh.abs(model.estimateRegressionParameters()[0]));

    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
