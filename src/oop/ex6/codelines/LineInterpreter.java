package oop.ex6.codelines;

import oop.ex6.variables.*;
import oop.ex6.scopes.*;

import java.util.ArrayList;
import java.util.regex.*;

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
        if(ReturnLine.isLine(line)){
            return CodeLineTypes.RETURN;
        }
        else if(line.trim().equals("}")){
            return CodeLineTypes.CLOSE_SCOPE;
        }
        else if(line.startsWith("//")){
            return CodeLineTypes.COMMENT;
        }
        else if(line.trim().equals("")){
            return CodeLineTypes.EMPTY_LINE;
        }
        if(VarInitLine.isLine(line)){
            return CodeLineTypes.VAR_DEFINITION;
        }
        else if(MethodDefLine.isLine(line)){
            return CodeLineTypes.OPEN_METHOD;
        }
		else if(IfWhileLine.isLine(line)){
            return CodeLineTypes.OPEN_IF_WHILE;
        }
        else if(VarAssignLine.isLine(line)){
            return CodeLineTypes.VAR_ASSIGNMENT;
        }
        else if(MethodCallLine.isLine(line)){
            return CodeLineTypes.METHOD_CALL;
        }
        else{
            return CodeLineTypes.ERROR;
        }

    }

    /**
     * Create all variables in a given line.
     * @param scope the scope in which the line is on.
     * @param lineNum The line number to create variables from.
     * @return An ArrayList of Variables.
     * @throws BadVariableDefinitionException If the line was not in correct format.
     */
    public static ArrayList<Variable> getVariables(Scope scope, int lineNum)
            throws CompileException
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];
        VarInitLine processor = new VarInitLine(line, lineNum);
        boolean isFinal = processor.isFinal();
        VarType type = VarType.StringToType(processor.getType());
        ArrayList<String> varsToAssignNames = processor.getNames();
        ArrayList<String> valuesNames = processor.getValues();
        ArrayList<Variable> result = new ArrayList<>();
        for (int i = 0; i < varsToAssignNames.size(); i++)
        {
            String varName = varsToAssignNames.get(i);
            if (valuesNames.get(i) == null)
            {
                if (isFinal)
                {
                    throw new BadVariableDefinitionException(i);
                }
                result.add(new Variable(type, varName, false, false));
                continue;
            }
            VarType argumentType = VarType.StringToType(valuesNames.get(i));
            if (argumentType == null)
            {
                Variable tmp = scope.findVariable(valuesNames.get(i));
                if(tmp == null){
                    throw new BadVariableDefinitionException(i);
                }
                if (tmp.getName().equals(varName))
                {
                    throw new BadVariableDefinitionException(i);
                }
                if (!tmp.isInitialized())
                {
                    throw new BadVariableDefinitionException(i);
                }
                if (!VarType.isAssignmentLegit(type, tmp.getType()))
                {
                    throw new BadVariableDefinitionException(i);
                }
                result.add(new Variable(type, varName, true, isFinal));
                continue;
            }
            else {
                if (!VarType.isAssignmentLegit(type, argumentType)) {
                    throw new BadVariableDefinitionException(i);
                }
                result.add(new Variable(type, varName, true, isFinal));
                continue;
            }
        }
        return result;
    }

    /**
     * Returns the method's name.
     * @param line The method definition line.
     * @return The method's name.
     */
    public static String getMethodName(String line)
    {
        line = line.substring(4); //reduce the void
        line = line.trim();
        line = line.split("\\(")[0];
        return line;
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
        VarAssignLine processor = new VarAssignLine(line, lineNum);
        String[] assignmentArguments =  processor.getAssignment();
        String toAssignName = assignmentArguments[0];
        Variable toAssignVar = scope.findVariable(toAssignName);
        if (toAssignVar == null)
        {
            throw new BadAssignmentException(lineNum);
        }
        if (toAssignVar.isFinal())
        {
            throw new BadAssignmentException(lineNum);
        }

        String toBeAssignName = assignmentArguments[1];
        VarType toBeAssignType = VarType.StringToType(toBeAssignName);
        if (toBeAssignType == null)
        {
            Variable toBeAssigned = scope.findVariable(toAssignName);
            if (toBeAssigned == null)
            {
                throw new BadAssignmentException(lineNum);
            }
            if (toBeAssigned.getType() != toAssignVar.getType())
            {
                throw new BadAssignmentException(lineNum);
            }
        }
        else
        {
            if (!VarType.isAssignmentLegit(toAssignVar.getType(), toBeAssignType))
            {
                throw new BadAssignmentException(lineNum);
            }
        }
        toAssignVar.setAsInitialized();
    }

    /**
     * Verifies a method call line.
     * @param scope the scope in which the line is on.
     * @param lineNum The line number to check.
     * @throws MethodCallException If the method call line was bad.
     */
    public static void verifyMethodCall(Scope scope, int lineNum) throws MethodCallException
    {
        String line = scope.getGlobalScope().getCodeLines()[lineNum];
        MethodCallLine processor = new MethodCallLine(line, lineNum);
        ArrayList<String> paramsName =  processor.getParameters();
        String methodName = processor.getName();
        MethodScope method = scope.getGlobalScope().findMethod(methodName);
        if (method == null)
        {
            throw new MethodCallException(lineNum);
        }
        ArrayList<Variable> methodParams = method.getParams();
        if (methodParams.size() != paramsName.size())
        {
            throw new MethodCallException(lineNum);
        }

        for (int i = 0; i < methodParams.size(); i++)
        {
            VarType argumentType = VarType.StringToType(paramsName.get(i));
            if (argumentType == null)
            {
                Variable var = scope.findVariable(paramsName.get(i));
                if (var == null)
                {
                    throw new MethodCallException(lineNum);
                }
                if (!var.isInitialized())
                {
                    throw new MethodCallException(lineNum);
                }
                if (!VarType.isAssignmentLegit(methodParams.get(i).getType(), var.getType()))
                {
                    throw new MethodCallException(lineNum);
                }

            }
            else
            {
                if (!VarType.isAssignmentLegit(methodParams.get(i).getType(), argumentType))
                {
                    throw new MethodCallException(lineNum);
                }
            }

        }
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
        return processor.getParams();
    }

    /**
     * Create all parameters for a given method.
     * @param scope The scope the line is defined in.
     * @param condition The condition line.
     * @param lineNum The line number to create variables from.
     * @throws BadIfWhileConditionException If the condition was not in correct format.
     */
    public static void verifyIfWhileCondition(Scope scope, String condition, int lineNum) throws
            BadIfWhileConditionException
    {
        IfWhileLine processor = new IfWhileLine(condition, lineNum);
        ArrayList<String> varNames =  processor.getVarsInCondition();
        for (String name : varNames)
        {
            Variable var = scope.findVariable(name);
            if (var == null)
            {
                throw new BadIfWhileConditionException(lineNum);
            }
            if (!var.isInitialized())
            {
                throw new BadIfWhileConditionException(lineNum);
            }
            if (!(var.getType() == VarType.BOOLEAN || var.getType() == VarType.DOUBLE || var.getType() ==
                    VarType.INT))
            {
                throw new BadIfWhileConditionException(lineNum);
            }
        }
    }
}
