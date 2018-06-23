package oop.ex6.scopes;

/**
 * An exception describing a problem in the if/while condition.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class BadIfWhileConditionException extends CompileException {

    /**
     * A constructor for the if/while condition exception.
     * @param line The line number of the exception.
     */
    public BadIfWhileConditionException(int line)
    {
        super("Bad If/While condition in line " + line + " is not in format");
    }
}
