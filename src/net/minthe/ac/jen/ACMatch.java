package net.minthe.ac.jen;

import net.minthe.ac.AlienReader;
import net.minthe.ac.math.LinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.ejml.simple.SimpleMatrix;
import org.jenetics.*;
import org.jenetics.engine.*;
import org.jenetics.util.DoubleRange;
import org.jenetics.util.LCG64ShiftRandom;

import java.util.HashMap;
import java.util.function.Function;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

/**
 * Created by Michael Kelley on 5/9/2017.
 */
public class ACMatch implements Problem<double[], DoubleGene, Double> {
    private static final int N_ROWS = 20;
    private static final int N_COLS = 20;

    static HashMap<Integer, SimpleMatrix> testValues;
    static double bestr2 = 0;
    static double[] best_ys;

    static {
        AlienReaderSM ar = new AlienReaderSM("C:\\Users\\purti\\ClionProjects\\aliencounter\\numbers.txt");
        testValues = ar.values;
    }
    public static double fness(double[] in) {
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
            xs[i - 1] = i;
            ys[i - 1] = m.get(0,0);
        }
        double sum = 0;
        for (int i = 1; i < testValues.size(); i++) {
            if (ys[i] > ys[i-1]) sum++;
        }
        OLSMultipleLinearRegression model = LinearRegression.polyReg(xs, ys, 1);
        double r2 = model.calculateRSquared();
        if (r2 > bestr2) {
            bestr2 = r2;
            best_ys = ys;
        }
        return sum * r2;
//        return model.calculateRSquared() * SimpleMatrixh.min(1, SimpleMatrixh.abs(model.estimateRegressionParameters()[0]));

    }

    @Override
    public Function<double[], Double> fitness() {
        return null;
    }

    @Override
    public Codec<double[], DoubleGene> codec() {
        return codecs.ofVector(DoubleRange.of(-10, 10));
    }

    public static void main(String[] args) {
//        ACSimpleMatrixch problem = new ACSimpleMatrixch();
//        DoubleChromosome d = DoubleChromosome.of(-10, 10, 225);
//        Engine.builder(problem.fitness(), null, null);
        Engine<DoubleGene, Double> engine = Engine.builder(ACMatch::fness, codecs.ofVector(DoubleRange.of(-10, 10), 840))
                .maximizing()
                .populationSize(100)
                .survivorsSelector(new TournamentSelector<>(5))
//                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(new Mutator<>(0.2), new SinglePointCrossover<>(0.2))
                .build();
        EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();
        long start = System.currentTimeMillis();
        Phenotype<DoubleGene, Double> best = engine.stream()
//                .limit(bySteadyFitness(10))
                .limit(100)
                .peek(stats)
                .collect(toBestPhenotype());
        long end = System.currentTimeMillis();
        System.out.println(stats.toString());
        System.out.println(best.toString());
        System.out.println(end-start);

//        for (int i = 0; i < testValues.size(); i++) {
//            System.out.print(best_ys[i] + " ");
//        }
    }
}
