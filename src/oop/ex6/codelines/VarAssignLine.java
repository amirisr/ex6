package oop.ex6.codelines;

import oop.ex6.scopes.BadAssignmentException;

import java.util.regex.Matcher;

/**
 * This class describes an analyser for variable assignment lines.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
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

	/**
	 * Constructor for the variable assignment analyser.
	 * @param line The line to analyze.
	 * @param lineNum The line number to analyze.
	 * @throws BadAssignmentException If the assignment is not legal.
	 */
	VarAssignLine(String line, int lineNum) throws BadAssignmentException{
		this.line = line;
		num = lineNum;
		processLine();
	}

	/**
	 * Returns if the line is a variable assignment line.
	 * @param line The line to analyze.
	 * @return true iff the line is a variable assignment line.
	 */
	public static boolean isLine(String line){
		return LineInterpreter.getMatcher(isLineRegex, line).matches();
	}

	private void processLine() throws BadAssignmentException{
		line = line.trim();
		line = line.substring(0, line.length()-1); // remove ';' suffix
		findName();
		assignedType = checkAssignment(line);
		if(assignedType == null){
			throw new BadAssignmentException(num);
		}
	}

	private void findName() throws BadAssignmentException{
		Matcher matcher = LineInterpreter.getMatcher(findNameRegex, line);
		if(!matcher.matches()){
			throw new BadAssignmentException(num);
		}
		else{
			assignedVar = matcher.group(1);
			line = line.substring(assignedVar.length()).trim(); // remove the variable name
			line = line.substring(1).trim(); // remove '=', we are now left with only the assignment value
		}
	}

	/**
	 * Matches the assignment line to the type the assignment is on.
	 * @param line The line to interpret.
	 * @return The type of the assignment.
	 */
	 public static String checkAssignment(String line){
		String type;
		if(LineInterpreter.getMatcher(stringRegex,line).matches()){
			type = VarInitLine.STRING;
		}
		else if(LineInterpreter.getMatcher(charRegex,line).matches()){
			type = VarInitLine.CHAR;
		}
		else if(LineInterpreter.getMatcher(intRegex,line).matches()){
			type = VarInitLine.INT;
		}
		else if(LineInterpreter.getMatcher(doubleRegex,line).matches()){
			type = VarInitLine.DOUBLE;
		}
		else if(LineInterpreter.getMatcher(booleanRegex, line).matches()){
			type = VarInitLine.BOOLEAN;
		}
		else if(LineInterpreter.getMatcher(VarInitLine.validVarNameRegex, line).matches()){
			type = line;
		}
		else {
			type = null;
		}
		return type;
	}

	/**
	 * Returns the assignments in the line.
	 * @return The assignments in the line.
	 */
	public String[] getAssignment(){
		String[] assignment = new String[2];
		assignment[0] = assignedVar;
		assignment[1] = assignedType;
		return assignment;
	}

}
