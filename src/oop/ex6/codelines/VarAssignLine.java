package oop.ex6.codelines;

import oop.ex6.scopes.BadAssignmentException;
import oop.ex6.scopes.BadVariableDefinition;

import java.util.regex.Matcher;

public class VarAssignLine {
	private static final String isLineRegex = "\\s*\\w*\\s*=.*;\\s*";
	private static final String findNameRegex = "("+VarInitLine.validVarNameRegex+")\\s*=.*";
	private static final String stringRegex = "\".*\"";
	private static final String intRegex = "[+-]?\\d+";
	private static final String doubleRegex = "[+-]?\\d+(?:\\.\\d+)";
	private static final String charRegex = "'.?'";
	private static final String booleanRegex = "true|false";
	private String line;
	private final int num;
	private String assignedVar;
	private String assignedType;


	VarAssignLine(String line, int lineNum) throws BadAssignmentException{
		this.line = line;
		num = lineNum;
		processLine();
	}

	static boolean isLine(String line){
		return LineInterpreter.getMatcher(line, isLineRegex).matches();
	}

	private void processLine() throws BadAssignmentException{
		line = line.trim().substring(0, line.length()-1); // remove ';' suffix
		findName();
		checkAssignment();
	}

	private void findName() throws BadAssignmentException{
		Matcher matcher = LineInterpreter.getMatcher(line, findNameRegex);
		if(!matcher.matches()){
			throw new BadAssignmentException(num);
		}
		else{
			assignedVar = matcher.group(1);
			line = line.substring(assignedVar.length()).trim(); // remove the variable name
			line = line.substring(1).trim(); // remove '=', we are now left with only the assignment value
		}
	}

	private void checkAssignment() throws BadAssignmentException{
		if(LineInterpreter.getMatcher(stringRegex,line).matches()){
			assignedType = VarInitLine.STRING;
		}
		else if(LineInterpreter.getMatcher(charRegex,line).matches()){
			assignedType = VarInitLine.CHAR;
		}
		else if(LineInterpreter.getMatcher(intRegex,line).matches()){
			assignedType = VarInitLine.INT;
		}
		else if(LineInterpreter.getMatcher(doubleRegex,line).matches()){
			assignedType = VarInitLine.DOUBLE;
		}
		else if(LineInterpreter.getMatcher(booleanRegex, line).matches()){
			assignedType = VarInitLine.BOOLEAN;
		}
		else if(LineInterpreter.getMatcher(VarInitLine.validVarNameRegex, line).matches()){
			assignedType = line;
		}
		else {
			throw new BadAssignmentException(num);
		}
	}

	String[] getAssignment(){
		String[] assignment = new String[2];
		assignment[0] = assignedVar;
		assignment[1] = assignedType;
		return assignment;
	}

}
