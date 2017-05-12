package net.minthe.ac.gen;

import java.util.List;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 7:59 PM
 */
public abstract class Function<T, O> {
    /**
     * The number of parameters that the function accepts.
     */
    private int arity;

    /**
     * Declares a new Function with the given arity. Note that
     * function is abstract, and this cannot be instantiated directly,
     * but either inheriting or a simple inline override of execute()
     * will suffice.
     * @param arity The number of parameters that the function accepts.
     */
    public Function(int arity) {
        this.arity = arity;
    }

    /**
     * Executes the given function checking that the list of parameters is valid.
     * @throws IllegalArgumentException Thrown if the list of parameters is not valid.
     * @param args Arguments with which to execute the function.
     * @return Returns the value of the function with the given parameters.
     */
    public O executeWrapper(List<T> args) {
        if(!hasValidArguments(args)) {
            throw new IllegalArgumentException("Number of parameters is not consistent with the arity of the function.");
        }
        return execute(args);
    }

    /**
     * Executes the given with no error checking.
     * @param args Arguments with which to execute the function.
     * @return Returns the value of the function with the given parameters.
     */
    public abstract O execute(List<T> args);

    /**
     * Checks if arguments of the function are valid. Does this by
     * comparing the arity of the function with the given size of the list.
     * @param args Argument list to check.
     * @return True if the arity and list of para
     */
    private boolean hasValidArguments(List<T> args) {
        return args.size() == arity;
    }

    /**
     * Get the arity of the function.
     * @return The number of parameters that the function takes.
     */
    public int getArity() {
        return arity;
    }

    @Override
    public String toString() {
        return "F";
    }
}
