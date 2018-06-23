package oop.ex6.codelines;

import oop.ex6.variables.Variable;
import oop.ex6.scopes.*;

import java.util.ArrayList;

public class LineInterpreter {

    public static CodeLineTypes getLineType(String line)
    {
        if(line.matches("\\s*(?:int|double|char|boolean|String)\\s*+.*}")){
            return CodeLineTypes.VAR_DEFINITION;
        }
        return null;
    }

    public static ArrayList<Variable> getVariables(String line, int lineNum)
    {
        return null;
    }

    public static boolean isAssignmentLegal(Scope scope, String line, int lineNum)
    {
        return false;
    }

    public static MethodScope createMethod(GlobalScope global, int startLine, int endLine)
    {
        return new MethodScope(global, startLine, endLine);
    }
}
