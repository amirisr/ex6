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

    public void addVariable(Variable variable, int lineNum) throws BadVariableDefinition
    {
        for (Variable tmp : vars)
        {
            if (variable.getName().equals(tmp.getName()))
            {
                throw new BadVariableDefinition(lineNum);
            }
        }
        vars.add(variable);
    }

    public void addVariablesFromArrayList(ArrayList<Variable> variables, int lineNum) throws BadVariableDefinition
    {
        for (Variable variable : variables)
        {
            addVariable(variable, lineNum);
        }
    }

    public abstract void testScope() throws CompileException;
}
