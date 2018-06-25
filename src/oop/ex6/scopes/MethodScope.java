
package oop.ex6.scopes;

import oop.ex6.codelines.*;
import oop.ex6.variables.*;

import java.util.*;

public class MethodScope extends Scope {

    private ArrayList<Variable> params;

    public MethodScope(GlobalScope global, int startLine, int endLine) throws BadMethodDefinitionException
    {
        super(global, startLine, endLine);
        params = LineInterpreter.getParameters(getGlobalScope().getCodeLines()[getStart()], getStart());
    }


    public ArrayList<Variable> getParams() {
        return params;
    }

    @Override
    public void testScope() throws CompileException {
        String[] codeLines = getGlobalScope().getCodeLines();
        int scopeStart = -1;
        int count = 0;
        for (int i = getStart() + 1; i <= getEnd(); i++)
        {
            String line = codeLines[i];
            switch (LineInterpreter.getLineType(line))
            {
                case EMPTY_LINE:
                case COMMENT:
                    break;
                case OPEN_IF_WHILE:
                    if (count > 0) {
                        count++;
                    }
                    else {
                        scopeStart = i;
                    }
                    break;
                case METHOD_CALL:
                    LineInterpreter.verifyMethodCall(this, i);
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0)
                    {
                        Scope tmp = new IfWhileScope(this, scopeStart, i);
                        tmp.testScope();
                    }
                    if (count < 0)
                    {
                        if (!(count == -1 && i == getEnd())) { // the definition line takes one count
                            throw new NumberOfScopeClosersException(i);
                        }
                    }
                    break;
                case VAR_ASSIGNMENT:
                    LineInterpreter.verifyAssignment(this, i);
                    break;
                case VAR_DEFINITION:
                    ArrayList<Variable> tmp = LineInterpreter.getVariables(this, i);
                    addVariablesFromArrayList(tmp, i);
                    break;
                case OPEN_METHOD:
                    throw new MethodDefinedInsideScopeException(i);
                default:
                    throw new SyntaxException(i);

            }

            if (count != -1) // the definition line takes one count
            {
                throw new NumberOfScopeClosersException(getEnd());
            }

        }
    }

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
}
