package oop.ex6.scopes;

import oop.ex6.variables.Variable;

import java.util.ArrayList;

public abstract class Scope {

    private Scope fatherScope;
    private ArrayList<Variable> vars;
    private int start;
    private int end;

    public Scope(Scope father, int startLine, int endLine){
        fatherScope = father;
        vars = new ArrayList<Variable>();
        start = startLine;
        end = endLine;
    }

    public Scope getFatherScope() {
        return fatherScope;
    }

    public GlobalScope getGlobalScope() {
        Scope tmp = this;
        while (tmp.fatherScope != null) {
            tmp = tmp.getFatherScope();
        }
        return (GlobalScope) tmp;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public ArrayList<Variable> getVariables() {
        return vars;
    }

    public void addVariablesFromArrayList(Variable var) {
        vars.add(var);
    }

    public void addVariablesFromArrayList(ArrayList<Variable> variables)
    {
        if (variables != null) { //ERASE LATER
            vars.addAll(variables);
        }
    }

    public abstract void testScope() throws CompileException;
}
