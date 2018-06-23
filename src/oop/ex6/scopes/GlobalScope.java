package oop.ex6.scopes;

import sun.reflect.generics.scope.MethodScope;

import java.util.ArrayList;

public class GlobalScope extends Scope
{
    private ArrayList<MethodScope> methods;
    private String[] codeLines;

    public GlobalScope(String[] lines){
        super(null, 0, lines.length - 1);
        methods = new ArrayList<MethodScope>();
        codeLines = lines;
    }

    public ArrayList<MethodScope> getMethods() {
        return methods;
    }

    public void addMethod(MethodScope method) {
        methods.add(method);
    }

    @Override
    public boolean testScope() {
        return false;
        //TODO
    }
}
