package oop.ex6.scopes;

/**
 * An exception describing a default syntax error.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class SyntaxException extends CompileException {
    private static final long serialVersionUID = 1L;

    /**
     * A constructor for the bad syntax exception.
     * @param line The line number of the exception.
     */
    public SyntaxException(int line)
    {
        super("Syntax error in line " + line);
    }
}
