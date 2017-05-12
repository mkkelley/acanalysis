package net.minthe.ac.gen;

import java.util.Map;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:13 PM
 */
public abstract class TerminalFunction<T, O> extends Function<T, O> implements Terminal<T, O> {

    /**
     * Initializes a new 0-arity function.
     * Execute must be overridden.
     */
    public TerminalFunction() {
        super(0);
    }

    @Override
    public O getValue() {
        return execute(null);
    }

    @Override
    public O evaluate(Map<TerminalVariable<T>, T> vars) {
        return getValue();
    }
}
