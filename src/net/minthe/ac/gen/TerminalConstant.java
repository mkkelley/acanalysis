package net.minthe.ac.gen;

import java.util.Map;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:07 PM
 */
public class TerminalConstant<T> implements Terminal<T, T> {

    private T value;

    /**
     * Create a new constant with the given value.
     * @param v The value of the constant.
     */
    public TerminalConstant(T v) {
        value = v;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public T evaluate(Map<TerminalVariable<T>, T> variableValues) throws IllegalArgumentException {
        return getValue();
    }
}
