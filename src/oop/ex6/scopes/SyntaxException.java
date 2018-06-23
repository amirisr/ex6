package oop.ex6.scopes;

public class SyntaxException extends Exception {

    public SyntaxException(int line)
    {
        super("Syntax error in line " + line);
    }
}
