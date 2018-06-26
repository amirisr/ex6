
package oop.ex6.scopes;

import oop.ex6.codelines.*;
import oop.ex6.variables.*;

import java.util.*;

public class MethodScope extends Scope {

    private ArrayList<Variable> params;
    private String name;
    private static int counterID = 0;

    public MethodScope(GlobalScope global, int startLine, int endLine) throws BadMethodDefinitionException
    {
        super(global, startLine, endLine);
        name = LineInterpreter.getMethodName(global.getCodeLines()[startLine]);
        params = LineInterpreter.getParameters(global.getCodeLines()[startLine], startLine);
    }


    public ArrayList<Variable> getParams() {
        return params;
    }

    @Override
    public void testScope() throws CompileException {
        ArrayList<Variable> history = getGlobalHistory();
        String[] codeLines = getGlobalScope().getCodeLines();
        int scopeStart = -1;
        int count = 0;
        boolean wasReturnFound = false;
        for (int i = getStart() + 1; i < getEnd(); i++) {
            String line = codeLines[i];
            switch (LineInterpreter.getLineType(line)) {
                case EMPTY_LINE:
                case COMMENT:
                    break;
                case OPEN_IF_WHILE:
                    if (count > 0) {
                        count++;
                    } else {
                        scopeStart = i;
                        count++;
                    }
                    wasReturnFound = false;
                    break;
                case METHOD_CALL:
                    if (count == 0) {
                        LineInterpreter.verifyMethodCall(this, i);
                    }
                    wasReturnFound = false;
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0) {
                        Scope tmp = new IfWhileScope(this, scopeStart, i);
                        tmp.testScope();
                    }
                    if (count < 0) {
                        throw new NumberOfScopeClosersException(i);
                    }
                    wasReturnFound = false;
                    break;
                case VAR_ASSIGNMENT:
                    if (count == 0) {
                        LineInterpreter.verifyAssignment(this, i);
                    }
                    wasReturnFound = false;
                    break;
                case VAR_DEFINITION:
                    if (count == 0) {
                        ArrayList<Variable> tmp = LineInterpreter.getVariables(this, i);
                        addVariablesFromArrayList(tmp, i);
                    }
                    wasReturnFound = false;
                    break;
                case OPEN_METHOD:
                    throw new MethodDefinedInsideScopeException(i);
                case RETURN:
                    wasReturnFound = true;
                    break;
                default:
                    throw new SyntaxException(i);

            }
        }
        if (count != 0 || LineInterpreter.getLineType(codeLines[getEnd()]) != CodeLineTypes.CLOSE_SCOPE ||
                !wasReturnFound) {
            throw new NumberOfScopeClosersException(getEnd());
        }
        getGlobalScope().setHistory(history);
    }

    /**
     * Adds an arraylist of variables to the scope.
     * @param variables The arraylist of variables.
     * @param lineNum The number of the line.
     * @throws BadVariableDefinition In case there is already a variable with the same name.
     */
    @Override
    public void addVariablesFromArrayList(ArrayList<Variable> variables, int lineNum) throws BadVariableDefinition {
        for (Variable variable : variables)
        {
            for (Variable argument : params)
            {
                if (variable.getName().equals(argument.getName()))
                {
                    throw new BadVariableDefinition(lineNum);
                }
            }
            addVariable(variable, lineNum);
        }
    }

    /**
     * Returns an arrayList of all variables and parameters - including father scopes.
     * @return An arrayList of all variables and parameters - including father scopes.
     */
    @Override
    public ArrayList<Variable> getAllVariables() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(super.getAllVariables());
        tmp.addAll(params);
        return tmp;
    }

    @Override
    public ArrayList<Variable> getVariables() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(super.getVariables());
        tmp.addAll(params);
        return tmp;
    }

    /**
     * Returns the name of the method.
     * @return The name of the method.
     */
    public String getName() {
        return name;
    }

    /* gets the variable's history of the global scope */
    private ArrayList<Variable> getGlobalHistory()
    {
        ArrayList<Variable> result = new ArrayList<>();
        for (Variable var : getGlobalScope().getVariables())
        {
            result.add(var.cloneVariable());
        }
        return result;
    }
}
