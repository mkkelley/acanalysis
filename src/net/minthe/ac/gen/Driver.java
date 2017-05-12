package net.minthe.ac.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 8:07 PM
 */
public class Driver {
    public static void main(String[] args) {
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
        double bestFitness = Double.MAX_VALUE;
        double currentFitness;
        Evaluable<Double> bestAnswer;
        Evaluable<Double> giveItAShot;
        long count = 0;
        while (bestFitness > .00000001) {
            giveItAShot = reb.generateRandomExpression(5, "grow");

            //System.out.println("giveItAShot.toString() = " + giveItAShot.toString());
            currentFitness = getFitness(giveItAShot, bestFitness, x);
            count++;
            if (currentFitness < bestFitness) {
                bestFitness = currentFitness;
                bestAnswer = giveItAShot;
                System.out.println("bestFitness = " + bestFitness);
                System.out.println("bestAnswer.toString() = " + bestAnswer.toString());
//                if (bestAnswer instanceof Expression) {
//                    System.out.println("((Expression<Double>)bestAnswer).getAllExpressions() = " + ((Expression<Double>) bestAnswer).getAllExpressions());
//                }
            }

        }
        System.out.println("count = " + count);
    }

    static Map<TerminalVariable<Double>, Double> varMap = new HashMap<>();

    @SafeVarargs
    public static double getFitness(Evaluable<Double> f, double max, TerminalVariable<Double>... vars) {
        double fitnessOffset = 0;
        for (double v = -2; fitnessOffset < max && v <= 2; v+=.1) {
            varMap.put(vars[0], v);
            fitnessOffset += Math.abs(getFuncVal(v) - f.evaluate(varMap));
            //fitnessOffset += Math.abs((Math.pow(v,2)+v+1) - f.evaluate(varMap));

        }
        varMap.clear();
        return fitnessOffset;
    }

    private static double getFuncVal(double d) {
        return d * d + d + 1;
    }
}
