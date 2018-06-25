package oop.ex6.codelines;

import java.util.ArrayList;
import oop.ex6.scopes.CompileException;

import javax.naming.CommunicationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfWhileLine{
	private int num;
	private String line;
	private final static String isLineRegex = "\\s*(?:while|if).*{\\s*";
	private final static String isConditionLegalRegex = "(?:\\s*(?:_\\w+|[a-zA-Z]\\w*|\\d+(?:\\.\\d+)?)\\s*" + "" +
												 "(?:\\|{2}|&{2}))+";
	private final static String booleanOperatorRegex = "\\|{2}|&{2}";
	private final static String notVarRegex = "true|false|\\d+(?:\\.\\d+)?";
	private final static String IF = "if";
	private final static String WHILE = "while";
	private ArrayList<String> varsInCondition;

	 IfWhileLine(String line, int lineNum) {
	 	num = lineNum;
	 	this.line = line;
	 	varsInCondition = new ArrayList<>();
	 	processLine();
	 }

	private static Matcher getMatcher(String regex, String string){
		Pattern p = Pattern.compile(regex);
		return p.matcher(string);
	}

	static boolean isLine(String line) {
		Matcher matcher = getMatcher(isLineRegex, line);
		return matcher.matches();
	}

	void processLine() {
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

	private void validateCondition() {
		line = line.substring(0, line.length() - 1).trim(); // remove the '{' char
		if (!line.startsWith("(") || !line.endsWith(")")) {
			// raise exception, condition is not bounded with parentheses
		}
		line = line.substring(1, line.length() - 1) + "||";
		if (!getMatcher(isConditionLegalRegex, line).matches()) {
			// raise exception
		}
		// we know the condition is legal, i.e. has no strings chars spaces etc
	}

	private void extractConditionals() {
		line = line.substring(0, line.length() - 2); // removed the '//' suffix
		String[] tmpConditionals = line.split(booleanOperatorRegex);
		for (int i = 0; i < tmpConditionals.length; i++) {
			String condition = tmpConditionals[i].trim();
			if (!getMatcher(notVarRegex, condition).matches()) {
				varsInCondition.add(condition); // the expression is not true/false nor a number, hence it
				// is a variable
			}
		}
	}

	ArrayList<String> getVarsInCondition() {
		return varsInCondition;
	}

}
