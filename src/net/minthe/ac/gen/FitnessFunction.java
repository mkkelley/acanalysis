package net.minthe.ac.gen;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Michael
 * Date: 6/28/12
 * Time: 7:44 PM
 */
public abstract class FitnessFunction<T> {

    protected Map<TerminalVariable<T>, T> varMap;

    public FitnessFunction() {
        varMap = new HashMap<>();
    }

    public abstract double evaluateFitness(Evaluable<T, O> expression, double maxFitness, TerminalVariable<T>... vars);

    public abstract double functionToEmulate(double... vars);
}
