package oop.ex6.scopes;

import oop.ex6.variables.Variable;

import java.util.ArrayList;

/**
 * This abstract class represents a scope in s-java code files.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public abstract class Scope {

    private Scope fatherScope;
    private ArrayList<Variable> vars;
    private int start;
    private int end;

    /**
     * A simple constructor for a general scope.
     * @param father The "father-scope".
     * @param startLine The first line in the code for this scope.
     * @param endLine The last line in the scope for this scope.
     */
    public Scope(Scope father, int startLine, int endLine){
        fatherScope = father;
        vars = new ArrayList<Variable>();
        start = startLine;
        end = endLine;
    }

    /**
     * Returns the father scope for this scope.
     * @return The father scope for this scope.
     */
    public Scope getFatherScope() {
        return fatherScope;
    }

    /**
     * Returns the global scope for this scope.
     * @return The global scope for this scope.
     */
    public GlobalScope getGlobalScope() {
        Scope tmp = this;
        while (tmp.fatherScope != null) {
            tmp = tmp.getFatherScope();
        }
        return (GlobalScope) tmp;
    }

    /**
     * Returns the first line for this scope.
     * @return The first line for this scope.
     */
    public int getStart() {
        return start;
    }

    /**
     * Returns the last line for this scope.
     * @return The last line for this scope.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Returns all the variables for this scope.
     * @return All the variables fot this scope.
     */
    public ArrayList<Variable> getVariables() {
        return vars;
    }

    /**
     * Adds a variable for this scope.
     * @param variable The variable to add.
     * @param lineNum The line of its declaration.
     * @throws BadVariableDefinition If this scope already has a variable with this name.
     */
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

    /**
     * Adds an array list of variables.
     * @param variables ArrayList of variables to add.
     * @param lineNum The line num of their declaration.
     * @throws BadVariableDefinition If this scope already has a variable with this name.
     */
    public void addVariablesFromArrayList(ArrayList<Variable> variables, int lineNum) throws
            BadVariableDefinition
    {
        for (Variable variable : variables)
        {
            addVariable(variable, lineNum);
        }
    }


    /**
     * Tests and verifies the s-java scope.
     * @throws CompileException In case the code is not legal.
     */
    public abstract void testScope() throws CompileException;

    /**
     * Finds the first variable definition hierarchy according to its name.
     * @param var The variable's name.
     * @return The first variable definition hierarchy according to its name.
     */
    public Variable findVariable(String var)
    {
        Scope pos = this;
        while (pos != null)
        {
            for (Variable tmp : pos.getVariables())
            {
                if (var.equals(tmp.getName()))
                {
                    return tmp;
                }
            }
            pos = pos.fatherScope;
        }
        return null;
    }

    /**
     * Sets the scope's variable back to a given history.
     * @param variables ArrayList of old variables.
     */
    public void setHistory(ArrayList<Variable> variables)
    {
        vars = variables;
    }
}
