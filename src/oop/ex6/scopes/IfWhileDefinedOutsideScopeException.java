package oop.ex6.scopes;

/**
 * An exception describing an if/while scope defined in the global scope.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class IfWhileDefinedOutsideScopeException extends CompileException {

    /**
     * A constructor for the if/while defined outside scope exception.
     * @param line The line number of the exception.
     */
    public IfWhileDefinedOutsideScopeException(int line)
    {
        super("If/While defined outside scope in line " + line);
    }
}
