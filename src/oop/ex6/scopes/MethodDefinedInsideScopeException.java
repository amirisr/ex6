package oop.ex6.scopes;

public class MethodDefinedInsideScopeException extends Exception {

    public MethodDefinedInsideScopeException(int line)
    {
        super("Method defined inside scope in line " + line);
    }
}
