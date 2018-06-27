package oop.ex6.scopes;

/**
 * An exception describing a method definition not in the main scope.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class MethodDefinedInsideScopeException extends CompileException {
    private static final long serialVersionUID = 1L;

    /**
     * A constructor for the method defined inside scope exception.
     * @param line The line number of the exception.
     */
    public MethodDefinedInsideScopeException(int line)
    {
        super("Method defined inside scope in line " + line);
    }
}
