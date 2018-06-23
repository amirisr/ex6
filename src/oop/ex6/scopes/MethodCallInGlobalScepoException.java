package oop.ex6.scopes;

public class MethodCallInGlobalScepoException extends Exception{

    public MethodCallInGlobalScepoException(int line)
    {
        super("Method call in global scope in line " + line);
    }
}
