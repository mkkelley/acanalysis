package net.minthe.ac.gen;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:18 PM
 */
public interface Terminal<T, O> extends Evaluable<T, O> {
    /**
     * Get the value of a given terminal. If your terminal
     * requires arguments, rethink it.
     * @return The value of the terminal.
     */
    public O getValue();
}
