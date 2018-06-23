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
        if(line.matches("\\s*(?:int|double|char|boolean|String)\\s*+.*;")){
            return CodeLineTypes.VAR_DEFINITION;
        }
        return CodeLineTypes.ERROR;
        //TODO
    }


    public static ArrayList<Variable> getVariables(String line, int lineNum) throws BadVariableDefinition
    {
        return null;
        //TODO
    }

    public static void verifyAssignment(Scope scope, String line, int lineNum) throws BadAssignmentException
    {
        return;
        //TODO
    }

    public static MethodScope createMethod(GlobalScope global, int startLine, int endLine)
            throws BadMethodDefinitionException
    {
        return new MethodScope(global, startLine, endLine);
    }

    public static void verifyMethodCall(Scope scope, String line, int lineNum) throws MethodCallException
    {
        return;
        //TODO
    }

    public static ArrayList<Variable> getParameters(String definition, int lineNum)
            throws BadMethodDefinitionException
    {
        return null;
        //TODO
    }

    public static void verifyIfWhileCondition(String line, int lineNum) throws BadIfWhileConditionException
    {
        return;
        //TODO
    }
}
