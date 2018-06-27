package oop.ex6.scopes;

/**
 * An exception describing a bad variable definition.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class BadVariableDefinitionException extends CompileException {
    private static final long serialVersionUID = 1L;

    /**
     * A constructor for the bad variable definition exception.
     * @param line The line number of the exception.
     */
    public BadVariableDefinitionException(int line)
    {
        super("Bad variable definition in line " + line + " is not in format");
    }
}
