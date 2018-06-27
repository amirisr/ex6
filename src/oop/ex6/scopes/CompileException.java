package oop.ex6.scopes;

/**
 * Simple facade class for all the exceptions in this exercise.
 * Extends Exception class.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class CompileException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Simple constructor for the compile exception.
     * @param message The message to send when the exception is raised.
     */
    public CompileException(String message)
    {
        super(message);
    }
}
