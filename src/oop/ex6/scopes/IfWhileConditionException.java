package oop.ex6.scopes;

public class IfWhileConditionException extends Exception {

    public IfWhileConditionException(int line)
    {
        super("If/While condition in line " + line + " is not in format");
    }
}
