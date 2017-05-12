package net.minthe.ac.wm;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael Kelley on 5/10/2017.
 */
public class DoubleArrayMutation implements EvolutionaryOperator<double[]> {
    double min;
    double max;
    Probability p;
    public DoubleArrayMutation(double min, double max, Probability p) {
        this.min = min;
        this.max = max;
        this.p   = p;
    }
    @Override
    public List<double[]> apply(List<double[]> selectedCandidates, Random rng) {
        List<double[]> out = new ArrayList<>(selectedCandidates.size());
        for (double[] candidate : selectedCandidates) {
            out.add(mutateArray(candidate, rng));
        }
        return out;
    }

    private double[] mutateArray(double[] array, Random rng) {
        double[] out = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            if (p.nextEvent(rng)) {
                out[i] = rng.nextDouble() * (max - min) + min;
            }
        }
        return out;
    }
}
