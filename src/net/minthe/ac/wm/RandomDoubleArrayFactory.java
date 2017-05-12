package net.minthe.ac.wm;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class RandomDoubleArrayFactory extends AbstractCandidateFactory<double[]> {
    private final int n;
    public RandomDoubleArrayFactory(int n) {
        this.n = n;
    }
    @Override
    public double[] generateRandomCandidate(Random rng) {
        double[] out = new double[n];
        for (int i = 0; i < n; i++) {
            out[i] = rng.nextDouble();
        }
        return out;
    }
}
