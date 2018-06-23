package oop.ex6.scopes;

public class OverScopeClosersException extends Exception {

    public OverScopeClosersException(int line)
    {
        super("Too much scope closers in line " + line);
    }
}
