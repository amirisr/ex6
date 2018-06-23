package oop.ex6.scopes;

public class BadAssignmentException extends Exception {

    public BadAssignmentException(int line)
    {
        super("Bad variable assignment in line " + line);
    }
}
