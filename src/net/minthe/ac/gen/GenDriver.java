package net.minthe.ac.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Michael
 * Date: 6/29/12
 * Time: 8:39 PM
 */
public class GenDriver {
    public static void main(String[] args) {
        FitnessFunctionDouble ff = new FitnessFunctionDouble() {
            @Override
            public double functionToEmulate(double... vars) {
                return vars[0] * vars[0] + vars[0] + 1;
            }
        };
        List<Function<Double>> functionSet = new ArrayList<>();
        functionSet.add(CommonFunctions.add);
        functionSet.add(CommonFunctions.subtract);
        functionSet.add(CommonFunctions.multiply);
        functionSet.add(CommonFunctions.divide);
        List<Terminal<Double>> termSet = new ArrayList<>();
        termSet.add(new TerminalConstant<>(1.0));
        termSet.add(new TerminalConstant<>(2.0));
        termSet.add(new TerminalConstant<>(3.0));
        termSet.add(new TerminalConstant<>(4.0));
        termSet.add(new TerminalConstant<>(5.0));
        termSet.add(new TerminalConstant<>(6.0));
        termSet.add(new TerminalConstant<>(7.0));
        termSet.add(new TerminalConstant<>(8.0));
        termSet.add(new TerminalConstant<>(9.0));
        termSet.add(new TerminalConstant<>(0.0));
        TerminalVariable<Double> x = new TerminalVariable<Double>() {
            @Override
            public String toString() {
                return "X";
            }
        };
        termSet.add(x);
        RandomExpressionBuilder<Double> reb = new RandomExpressionBuilder<>(functionSet, termSet);

        Generation<Double> g = new Generation<>(reb, 5);
        g.evaluateFitnessOfAll(ff, x);
        System.out.println("g.getBestEvaluable() = " + g.getBestEvaluable());
        System.out.println("g.getFitnessOfEvaluable(g.getBestEvaluable()) = " + g.getFitnessOfEvaluable(g.getBestEvaluable()));
    }
}
