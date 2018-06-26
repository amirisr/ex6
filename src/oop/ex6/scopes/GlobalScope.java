package oop.ex6.scopes;

import oop.ex6.codelines.LineInterpreter;
import oop.ex6.variables.Variable;

import java.util.ArrayList;

/**
 * This class represents a global scope.
 * Since it's a scope it extends the scope.java class.
 * @author Amir Israeli.
 * @author Omer Binyamin.
 */
public class GlobalScope extends Scope {
    private ArrayList<MethodScope> methods;
    private String[] codeLines;

    /**
     * A constructor for global scopes.
     * @param lines All the code lines in the file.
     */
    public GlobalScope(String[] lines) {
        super(null, 0, lines.length - 1);
        methods = new ArrayList<MethodScope>();
        codeLines = lines;
    }

    /**
     * Returns all the methods in this scope.
     * @return All the methods in this scope.
     */
    public ArrayList<MethodScope> getMethods() {
        return methods;
    }

    /**
     * Adds a new method to this scope.
     * @param method The method to add.
     * @param numLine The line number it was defined.
     * @throws BadMethodDefinitionException If there is already a method in this scope with the same name.
     */
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

    /**
     * Returns all the code lines.
     * @return All the code lines.
     */
    public String[] getCodeLines() {
        return codeLines;
    }

    /**
     * Tests the scope for any s-java compilation errors.
     * @throws CompileException If there are s-java compilation errors.
     */
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

    /**
     * Returns the method with the given name.
     * @param name the name to check.
     * @return The method with the given name.
     */
    public MethodScope findMethod(String name)
    {
        for (MethodScope method : methods)
        {
            if (method.getName().equals(name))
            {
                return method;
            }
        }
        return null;
    }
}
