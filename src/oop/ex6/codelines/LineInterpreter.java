package oop.ex6.codelines;

import oop.ex6.variables.Variable;
import oop.ex6.scopes.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Utility class for all functions necessary for "translating" the code lines (Strings) to appropriate
 * data types.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class LineInterpreter {
    static Matcher getMatcher(String regex, String string){
        Pattern p = Pattern.compile(regex);
        return p.matcher(string);
    }

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
        else if(line.trim().equals("}")){
            return CodeLineTypes.CLOSE_SCOPE;
        }
        else if(line.trim().equals("")){
        	return CodeLineTypes.EMPTY_LINE;
		}
		else if(line.startsWith("//")){
        	return CodeLineTypes.COMMENT;
		}
		else if(IfWhileLine.isLine(line)){
            return CodeLineTypes.OPEN_IF_WHILE;
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
            throws CompileException
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];
        VarInitLine processor = new VarInitLine(line, lineNum);
        return processor.getVars();
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
        MethodDefLine processor = new MethodDefLine(definition, lineNum);
        processor.processLine();
        return processor.getParams();
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
        IfWhileLine processor = new IfWhileLine(condition, lineNum);
        processor.processLine();
        ArrayList<String> varNames =  processor.getVarsInCondition();
        //TODO
    }
}
