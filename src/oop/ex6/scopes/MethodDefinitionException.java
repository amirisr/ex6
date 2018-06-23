package oop.ex6.scopes;

public class MethodDefinitionException extends Exception {

    public MethodDefinitionException(int line)
    {
        super("Method definition in line " + line + " is not in format");
    }
}
