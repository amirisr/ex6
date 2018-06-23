package oop.ex6.scopes;

public class IfWhileDefinedOutsideScopeException extends Exception {

    public IfWhileDefinedOutsideScopeException(int line)
    {
        super("If/While defined outside scope in line " + line);
    }
}
