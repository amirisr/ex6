package oop.ex6.scopes;

/**
 * An exception describing a bad method definition.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class BadMethodDefinitionException extends CompileException {

    /**
     * A constructor for the bad method definition exception.
     * @param line The line number of the exception.
     */
    public BadMethodDefinitionException(int line)
    {
        super("Bad method definition in line " + line + " is not in format");
    }
}
