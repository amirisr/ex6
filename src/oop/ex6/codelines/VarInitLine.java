package oop.ex6.codelines;

import oop.ex6.scopes.BadVariableDefinition;
import oop.ex6.variables.VarTypes;
import oop.ex6.variables.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarInitLine extends Line {
	private boolean isFinal = false;
	private String type;
	private ArrayList<String> names = new ArrayList<>();
	private ArrayList<String> values = new ArrayList<>();
	private static final String isLineRegex = "\\s*(?:final\\s)?\\s*(?:int|double|char|boolean|String)\\s+.*;\\s*";
	static final String validVarNameRegex = "_\\w+|[a-zA-Z]\\w*";
	private static final String stringRegex = "(" + validVarNameRegex + ")\\s*(?:=\\s*(\".*\")\\s*)?,";
	private static final String intRegex = "(" + validVarNameRegex + ")\\s*(?:=\\s*(\\d+)\\s*)?,";
	private static final String doubleRegex = "(" + validVarNameRegex + ")\\s*(?:=\\s*(\\d+(?:\\.\\d+)?)\\s*)?,";
	private static final String charRegex = "(" + validVarNameRegex + ")\\s*(?:=\\s*('.')\\s*)?,";
	private static final String booleanRegex = "(" + validVarNameRegex + ")\\s*(?:=\\s*(true|false)\\s*)?,";
	static final String FINAL = "final";
	private static final String INT = "int";
	private static final String DOUBLE = "double";
	private static final String STRING = "String";
	private static final String CHAR = "char";
	private static final String BOOLEAN = "boolean";

	public VarInitLine(String line) {
		super(line);
	}

	static boolean isLine(String line) {
		Matcher matcher = getMatcher(isLineRegex, line);
		return matcher.matches();
	}

	void processLine() {
		line = line.trim(); // remove excess spaces
		line = line.substring(0, line.length() - 1) + ","; // replace the ';' with ','
		checkFinal();
		getVarType();
		findVarsAndValues();
	}

	/**
	 * @return the variables initiated in this line
	 */
	ArrayList<Variable> getVars() {
		ArrayList<Variable> vars = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			vars.add(new Variable(VarTypes.StringToType(type), names.get(i), values.get(i) != null, isFinal));
		}
		return vars;
	}

	/*
	extract the "final" keyword if it exists
	*/
	private void checkFinal() {
		if (line.startsWith(FINAL)) {
			isFinal = true;
			line = line.substring(FINAL.length()).trim(); // remove the "final"
		}
	}

	/*
	extract the type of the variables
	 */
	private void getVarType() {
		type = line.split(" ")[0]; // we checked there is a space after the type declaration
		line = line.substring(type.length()); // remove the type
		line = line.trim(); // we are left with the variables and their values
	}

	/*
	find and extract all of the vars initiated and their values if assigned
	 */
	private void findVarsAndValues() {
		String regex = "";
		switch (type) {
			case INT:
				regex = intRegex;
			case DOUBLE:
				regex = doubleRegex;
			case STRING:
				regex = stringRegex;
			case BOOLEAN:
				regex = booleanRegex;
			case CHAR:
				regex = charRegex;
			default:
				// raise exception, we shouldn't reach here
		}
		Matcher matcher = getMatcher(regex, line);
		int prevEnd = 0;
		while (matcher.find()) {
			if (matcher.start() != prevEnd + 1) {
				return;// raise exception, the regex skipped a part of the line
			}
			names.add(matcher.group(1));
			values.add(matcher.group(2));
		}

	}


}
