package oop.ex6.scopes;

/**
 * An exception describing a bad variable assignment.
 * Extends compile exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class BadAssignmentException extends CompileException {

    /**
     * A constructor for the bad variable exception.
     * @param line The line number of the exception.
     */
    public BadAssignmentException(int line)
    {
        super("Bad variable assignment in line " + line);
    }
}
