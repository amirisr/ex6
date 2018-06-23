package oop.ex6.scopes;

/**
 * An exception describing a bad method call.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class MethodCallException extends CompileException {

    /**
     * A constructor for the method call exception.
     * @param line The line number of the exception.
     */
    public MethodCallException(int line)
    {
        super("Bad method call in line " + line + " is not in format");
    }
}
