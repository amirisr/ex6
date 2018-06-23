package oop.ex6.scopes;

/**
 * An exception describing too many scope closers ('{')}.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class OverScopeClosersException extends CompileException {

    /**
     * A constructor for the over scope closers exception.
     * @param line The line number of the exception.
     */
    public OverScopeClosersException(int line)
    {
        super("Too much scope closers in line " + line);
    }
}
