package oop.ex6.scopes;

import sun.reflect.generics.scope.MethodScope;

import java.util.ArrayList;

public class GlobalScope extends Scope
{
    private ArrayList<MethodScope> methods;
    private String[] codeLines;

    public GlobalScope(int start, int end, String[] lines){
        super(null, start, end);
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
