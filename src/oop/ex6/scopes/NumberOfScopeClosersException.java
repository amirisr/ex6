package oop.ex6.scopes;

/**
 * An exception describing not good number of scope closers ('{')}.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class NumberOfScopeClosersException extends CompileException {
    private static final long serialVersionUID = 1L;

    /**
     * A constructor for the over scope closers exception.
     * @param line The line number of the exception.
     */
    public NumberOfScopeClosersException(int line)
    {
        super("Not good number of closers in line " + line);
    }
}
