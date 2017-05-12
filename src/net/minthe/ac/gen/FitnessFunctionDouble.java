package net.minthe.ac.gen;

/**
 * User: Michael
 * Date: 6/28/12
 * Time: 8:31 PM
 */
public abstract class FitnessFunctionDouble extends FitnessFunction<Double> {

    @Override
    @SafeVarargs
    public final double evaluateFitness(Evaluable<Double> expression, double max, TerminalVariable<Double>... vars) {
        double fitnessOffset = 0;
        for (double d = -2; fitnessOffset < max && d <= 2; d+=.1) {
            varMap.put(vars[0], d);
            fitnessOffset += Math.abs(functionToEmulate(d) - expression.evaluate(varMap));
            varMap.clear();
        }
        return fitnessOffset;
    }
}
