
package oop.ex6.scopes;

import oop.ex6.codelines.*;
import oop.ex6.variables.*;

import java.util.*;

public class MethodScope extends Scope {

    private ArrayList<Variable> params;
    private String name;

    public MethodScope(GlobalScope global, int startLine, int endLine) throws BadMethodDefinitionException
    {
        super(global, startLine, endLine);
        params = LineInterpreter.getParameters(global.getCodeLines()[getStart()], getStart());
        System.out.println(startLine+1);
        System.out.println(endLine+1);
        for (Variable p : params)
        {
            System.out.println(p);
        }
        System.out.println();
    }


    public ArrayList<Variable> getParams() {
        return params;
    }

    @Override
    public void testScope() throws CompileException {
        String[] codeLines = getGlobalScope().getCodeLines();
        int scopeStart = -1;
        int count = 0;
        for (int i = getStart() + 1; i < getEnd(); i++)
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
                    if (count == 0) {
                        LineInterpreter.verifyMethodCall(this, i);
                    }
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0)
                    {
                        Scope tmp = new IfWhileScope(this, scopeStart, i);
                        tmp.testScope();
                    }
                    if (count < 0) {
                        throw new NumberOfScopeClosersException(i);
                    }
                    break;
                case VAR_ASSIGNMENT:
                    if (count == 0) {
                        LineInterpreter.verifyAssignment(this, i);
                    }
                    break;
                case VAR_DEFINITION:
                    if (count == 0) {
                        ArrayList<Variable> tmp = LineInterpreter.getVariables(this, i);
                        for (Variable var : tmp)
                        {
                            System.out.println("\t"+var);
                        }
                        addVariablesFromArrayList(tmp, i);
                    }
                    break;
                case OPEN_METHOD:
                    throw new MethodDefinedInsideScopeException(i);
                default:
                    throw new SyntaxException(i);

            }

            if (count != 0 || LineInterpreter.getLineType(codeLines[getEnd()]) != CodeLineTypes.CLOSE_SCOPE)
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

    @Override
    public ArrayList<Variable> getAllVariables() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(super.getAllVariables());
        tmp.addAll(params);
        return tmp;
    }
}
