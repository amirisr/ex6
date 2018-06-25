package oop.ex6.codelines;

import oop.ex6.variables.Variable;
import oop.ex6.scopes.*;

import java.util.ArrayList;

/**
 * A Utility class for all functions necessary for "translating" the code lines (Strings) to appropriate
 * data types.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class LineInterpreter {

    /**
     * Matches a code line to its type.
     * @param line The line to interpret.
     * @return A CodeLineType.
     */
    public static CodeLineTypes getLineType(String line)
    {
        if(VarInitLine.isLine(line)){
            return CodeLineTypes.VAR_DEFINITION;
        }
        else if(MethodDefLine.isLine(line)){
            return CodeLineTypes.OPEN_METHOD;
        }
        return CodeLineTypes.ERROR;
        //TODO
    }

    /**
     * Create all variables in a given line.
     * @param scope the scope in which the line is on.
     * @param lineNum The line number to create variables from.
     * @return An ArrayList of Variables.
     * @throws BadVariableDefinition If the line was not in correct format.
     */
    public static ArrayList<Variable> getVariables(Scope scope, int lineNum)
            throws BadVariableDefinition
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];
        VarInitLine processor = new VarInitLine(line);
        return null;
        //TODO
    }

    /**
     * Verifies an assignment line.
     * @param scope the scope in which the line is on.
     * @param lineNum The line number to check.
     * @throws BadAssignmentException If the assignment line was bad.
     */
    public static void verifyAssignment(Scope scope, int lineNum) throws BadAssignmentException
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];

        return;
        //TODO
    }

    /**
     * Verifies a method call line.
     * @param scope the scope in which the line is on.
     * @param lineNum The line number to check.
     * @throws BadAssignmentException If the method call line was bad.
     */
    public static void verifyMethodCall(Scope scope, int lineNum) throws MethodCallException
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];

        return;
        //TODO
    }

    /**
     * Create all parameters for a given method.
     * @param definition The definition line.
     * @param lineNum The line number to create variables from.
     * @return An ArrayList of Variables.
     * @throws BadMethodDefinitionException If the line was not in correct format.
     */
    public static ArrayList<Variable> getParameters(String definition, int lineNum)
            throws BadMethodDefinitionException
    {
        return null;
        //TODO
    }

    /**
     * Create all parameters for a given method.
     * @param condition The condition line.
     * @param lineNum The line number to create variables from.
     * @throws BadIfWhileConditionException If the condition was not in correct format.
     */
    public static void verifyIfWhileCondition(String condition, int lineNum) throws
            BadIfWhileConditionException
    {
        return;
        //TODO
    }
}
