package net.minthe.ac.gen;

import java.util.Map;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 8:59 PM
 */
public interface Evaluable<T, O> {
    public O evaluate(Map<TerminalVariable<T>,T> variableValues) throws IllegalArgumentException;
}
