package oop.ex6.codelines;

import com.sun.xml.internal.bind.v2.TODO;
import oop.ex6.variables.Variable;
import oop.ex6.scopes.*;

import java.util.ArrayList;

public class LineInterpreter {

    public static CodeLineTypes getLineType(String line)
    {
        if(line.matches("\\s*(?:int|double|char|boolean|String)\\s*+.*;")){
            return CodeLineTypes.VAR_DEFINITION;
        }
        return CodeLineTypes.ERROR;
        //TODO
    }

    public static ArrayList<Variable> getVariables(String line, int lineNum)
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
            throws MethodDefinitionException
    {
        return new MethodScope(global, startLine, endLine);
    }

    public static void verifyMethodCall(Scope scope, String line, int lineNum) throws MethodCallException
    {
        return;
        //TODO
    }

    public static ArrayList<Variable> getParameters(String definition, int lineNum)
            throws MethodDefinitionException
    {
        return null;
        //TODO
    }

    public static void verifyIfWhileCondition(String line, int lineNum) throws IfWhileConditionException
    {
        return;
        //TODO
    }
}
