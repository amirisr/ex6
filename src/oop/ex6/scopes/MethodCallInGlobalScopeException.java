package oop.ex6.scopes;

/**
 * An exception describing a method call in the global scope.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class MethodCallInGlobalScopeException extends CompileException{

    /**
     * A constructor for the method call in global scope exception.
     * @param line The line number of the exception.
     */
    public MethodCallInGlobalScopeException(int line)
    {
        super("Method call in global scope in line " + line);
    }
}
