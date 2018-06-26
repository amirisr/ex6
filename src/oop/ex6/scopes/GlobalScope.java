package oop.ex6.scopes;

import oop.ex6.codelines.LineInterpreter;
import oop.ex6.variables.Variable;

import java.util.ArrayList;

public class GlobalScope extends Scope {
    private ArrayList<MethodScope> methods;
    private String[] codeLines;

    public GlobalScope(String[] lines) {
        super(null, 0, lines.length - 1);
        methods = new ArrayList<MethodScope>();
        codeLines = lines;
    }

    public ArrayList<MethodScope> getMethods() {
        return methods;
    }

    public void addMethod(MethodScope method, int numLine) throws BadMethodDefinitionException{
        for (MethodScope scope : methods)
        {
            if (scope.getName().equals(method.getName()))
            {
                throw new BadMethodDefinitionException(numLine);
            }
        }
        methods.add(method);
    }

    public String[] getCodeLines() {
        return codeLines;
    }

    @Override
    public void testScope() throws CompileException {
        int methodStart = -1;
        int count = 0;
        for (int i = getStart(); i <= getEnd(); i++) {
            String line = codeLines[i];
            switch (LineInterpreter.getLineType(line)) {
                case EMPTY_LINE:
                case COMMENT:
                    break;
                case OPEN_IF_WHILE:
                    if (count > 0) {
                        count++;
                    } else {
                        throw new IfWhileDefinedOutsideScopeException(i);
                    }
                    break;
                case OPEN_METHOD:
                    if (count > 0) {
                        throw new MethodDefinedInsideScopeException(i);
                    } else {
                        methodStart = i;
                        count++;
                    }
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0) {
                        MethodScope tmp = new MethodScope(this, methodStart, i);
                        addMethod(tmp, methodStart);
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
                        addVariablesFromArrayList(tmp, i);
                    }
                    break;
                case METHOD_CALL:
                    if (count == 0) {
                        throw new MethodCallInGlobalScopeException(i);
                    }
                    break;
                case RETURN:
                    if (count == 0)
                    {
                        throw new SyntaxException(i);
                    }
                    break;
                default:
                    throw new SyntaxException(i);
            }
        }

        if (count != 0)
        {
            throw new NumberOfScopeClosersException(getEnd());
        }

        for (Scope scope : methods) {
            scope.testScope();
        }
    }

}
