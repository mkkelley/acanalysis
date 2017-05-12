package net.minthe.ac.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 7:59 PM
 */
public class RandomExpressionBuilder<T> {

    private List<Function<T>> functionSet;
    private List<Terminal<T>> terminalSet;
    private Random r;

    /**
     * Create a new RandomExpressionBuilder with a given set of both terminals and
     * functions.
     * @param funcs The set of non-0-arity functions that can be used.
     * @param terms The set of constants, variables, and 0-arity functions
     *              that can be used.
     */
    public RandomExpressionBuilder(List<Function<T>> funcs, List<Terminal<T>> terms) {
        functionSet = funcs;
        terminalSet = terms;
        r = new Random();
    }

    /**
     * Generate a random expression with the constructor's set of functions and terminals.
     * @param maxDepth The maximum nested depth of the expression.
     * @param method If this is equal to "grow", apply the grow algorithm, else apply
     *               the "full" algorithm.
     * @return A random expression with the given constraints.
     */
    public Evaluable<T> generateRandomExpression(int maxDepth, String method) {
        if(maxDepth == 0 || growShouldStop(method)) {
            return getRandomTerminal();
        }
        Function<T> f = getRandomFunction();
        List<Evaluable<T>> argSet = new ArrayList<>();
        for(int i = 0; i < f.getArity(); i++) {
            argSet.add(generateRandomExpression(maxDepth - 1, method));
        }
        return new Expression<>(f,argSet);
    }

    private Terminal<T> getRandomTerminal() {
        return terminalSet.get(r.nextInt(terminalSet.size()));
    }

    private Function<T> getRandomFunction() {
        return functionSet.get(r.nextInt(functionSet.size()));
    }

    /**
     * Randomly decide if a grow function should stop generating new branches.
     * @param func The function.
     * @return True if the expression should stop branching.
     */
    private boolean growShouldStop(String func) {
        return (func.equals("grow") &&
                Math.random() < (double)terminalSet.size()/(terminalSet.size()+functionSet.size()));
    }
}
