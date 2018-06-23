package oop.ex6.scopes;

public class MethodCallException extends Exception {

    public MethodCallException(int line)
    {
        super("Method call in line " + line + " is not in format");
    }
}
