package oop.ex6.codelines;

import java.util.ArrayList;

import oop.ex6.scopes.BadIfWhileConditionException;

import java.util.regex.Matcher;

/**
 * This class describes an analyser for if/while condition lines.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public class IfWhileLine{
	private int num;
	private String line;
	private final static String isLineRegex = "\\s*(?:while|if).*\\{\\s*";
	private final static String isConditionLegalRegex = "(?:\\s*(?:_\\w+|[a-zA-Z]\\w*|[+-]?\\d+(?:\\.\\d+)" +
																"?)\\s*(?:\\|{2}|&{2}))+";
	private final static String booleanOperatorRegex = "\\|{2}|&{2}";
	private final static String notVarRegex = "true|false|[+-]?\\d+(?:\\.\\d+)?";
	private final static String IF = "if";
	private final static String WHILE = "while";
	private ArrayList<String> varsInCondition;

	/**
	 * Constructor for the if/while condition analyser.
	 * @param line The line to analyze.
	 * @param lineNum The line number to analyze.
	 * @throws BadIfWhileConditionException If the condition is not legal.
	 */
	public IfWhileLine(String line, int lineNum) throws BadIfWhileConditionException{
	 	num = lineNum;
	 	this.line = line;
	 	varsInCondition = new ArrayList<>();
	 	processLine();
	}


	/**
	 * Returns if a given line is a if/while condition line.
	 * @param line The line to analyze.
	 * @return true iff the line is if/while condition.
	 */
	public static boolean isLine(String line) {
		Matcher matcher = LineInterpreter.getMatcher(isLineRegex, line);
		return matcher.matches();
	}

	private void processLine() throws BadIfWhileConditionException{
		line = line.trim();
		removeIfWhile();
		validateCondition();
		extractConditionals();
	}

	/*
	remove the if or the while prefix
	 */
	private void removeIfWhile() {
		if (line.startsWith(IF)) {
			line = line.substring(IF.length());
		} else {
			line = line.substring(WHILE.length());
		}
		line = line.trim();
	}

	private void validateCondition() throws BadIfWhileConditionException{
		line = line.substring(0, line.length() - 1).trim(); // remove the '{' char
		if (!line.startsWith("(") || !line.endsWith(")")) {
			throw new BadIfWhileConditionException(num);
		}
		line = line.substring(1, line.length() - 1) + "||";
		if (!LineInterpreter.getMatcher(isConditionLegalRegex, line).matches()) {
			throw new BadIfWhileConditionException(num);
		}
		// we know the condition is legal, i.e. has no strings chars spaces etc
	}

	private void extractConditionals() {
		line = line.substring(0, line.length() - 2); // removed the '//' suffix
		String[] tmpConditionals = line.split(booleanOperatorRegex);
		for (int i = 0; i < tmpConditionals.length; i++) {
			String condition = tmpConditionals[i].trim();
			if (!LineInterpreter.getMatcher(notVarRegex, condition).matches()) {
				varsInCondition.add(condition); // the expression is not true/false nor a number, hence it
				// is a variable
			}
		}
	}

	/**
	 * Returns the variables in the condition line.
	 * @return The variables in the condition line.
	 */
	public ArrayList<String> getVarsInCondition() {
		return varsInCondition;
	}

}
