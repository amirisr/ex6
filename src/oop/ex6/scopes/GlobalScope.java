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

    public void addMethod(MethodScope method) {
        methods.add(method);
    }

    public String[] getCodeLines() {
        return codeLines;
    }

    @Override
    public void testScope() throws CompileException {
        for (int i = getStart(); i <= getEnd(); i++) {
            String line = codeLines[i];
            int methodStart = -1;
            int count = 0;
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
                        addMethod(new MethodScope(this, methodStart, i));
                    }
                    if (count < 0) {
                        throw new OverScopeClosersException(i);
                    }
                    break;
                case VAR_ASSIGNMENT:
                    LineInterpreter.verifyAssignment(this, i);
                    break;
                case VAR_DEFINITION:
                    ArrayList<Variable> tmp = LineInterpreter.getVariables(this, i);
                    addVariablesFromArrayList(tmp);
                    break;
                case METHOD_CALL:
                    throw new MethodCallInGlobalScopeException(i);
                default:
                    throw new SyntaxException(i);

            }
        }

        for (Scope scope : methods) {
            scope.testScope();
        }
    }
}
