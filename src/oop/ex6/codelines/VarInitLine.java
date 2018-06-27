package oop.ex6.codelines;

import oop.ex6.scopes.BadVariableDefinitionException;
import oop.ex6.variables.*;

import java.util.ArrayList;
import java.util.regex.*;

import static oop.ex6.codelines.LineInterpreter.getMatcher;

/**
 * This class describes an analyser for variable declaration lines.
 *
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public class VarInitLine {
	private String line;
	private int num;
	private boolean isFinal = false;
	private String type;
	private ArrayList<String> names;
	private ArrayList<String> values;
	private static final String isLineRegex = "\\s*(?:final\\s)?\\s*(?:int|double|char|boolean|String)\\s+" +
            ".*;\\s*";
	public static final String validVarNameRegex = "_\\w+|[a-zA-Z]\\w*";

	private static final String stringRegex = "\\s*(" + validVarNameRegex + ")\\s*(?:=\\s*(\".*\"|" +
            validVarNameRegex + ")\\s*)?,";
	private static final String intRegex = "\\s*(" + validVarNameRegex + ")\\s*(?:=\\s*([+-]?\\d+|" +
            validVarNameRegex + ")\\s*)?,";
	private static final String doubleRegex = "\\s*(" + validVarNameRegex + ")\\s*(?:=\\s*([+-]?\\d+(?:\\" +
            ".\\d+)" + "?|" + validVarNameRegex + ")\\s*)?,";
	private static final String charRegex = "\\s*(" + validVarNameRegex + ")\\s*(?:=\\s*('.'|" +
            validVarNameRegex + ")\\s*)?,";
	private static final String booleanRegex = "\\s*(" + validVarNameRegex + ")\\s*(?:=\\s*" + "" +
            "(true|false|[+-]?\\d+(?:\\.\\d+)?|" + validVarNameRegex + ")\\s*)?,";

	static final String FINAL = "final";

	static final String INT = "int";
	static final String DOUBLE = "double";
	static final String STRING = "String";
	static final String CHAR = "char";
	static final String BOOLEAN = "boolean";
	static final String FALSE = "false";
	static final String TRUE = "true";

	/**
	 * Constructor for a variable declaration line analyser.
	 *
	 * @param line    The line to analyze.
	 * @param lineNum The line number in its code.
	 * @throws BadVariableDefinitionException In case the line is not legit line.
	 */
	public VarInitLine(String line, int lineNum) throws BadVariableDefinitionException
    {
		this.line = line;
		num = lineNum;
		names = new ArrayList<String>();
		values = new ArrayList<String>();
		processLine();
	}

	/**
	 * Determines if a given line describes a variable declaration or not.
	 *
	 * @param line The line to analyze.
	 * @return true iff the line describes a variable declaration line.
	 */
	public static boolean isLine(String line) {
		Matcher matcher = LineInterpreter.getMatcher(isLineRegex, line);
		return matcher.matches();
	}

	private void processLine() throws BadVariableDefinitionException
    {
		line = line.trim(); // remove excess spaces
		line = line.substring(0, line.length() - 1) + ","; // replace the ';' with ','
		checkFinal();
		getVarType();
		findVarsAndValues();
	}

	/**
	 * Returns the variables initialized in the line.
	 *
	 * @return The variables initialized in the line.
	 */
	public ArrayList<Variable> getVars() {
		ArrayList<Variable> vars = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			vars.add(new Variable(VarType.StringToType(type), names.get(i), values.get(i) != null,
                    isFinal));
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
	private void findVarsAndValues() throws BadVariableDefinitionException
    {
		String regex = "";
		switch (type) {
			case INT:
				regex = intRegex;
				break;
			case DOUBLE:
				regex = doubleRegex;
				break;
			case STRING:
				regex = stringRegex;
				break;
			case BOOLEAN:
				regex = booleanRegex;
				break;
			case CHAR:
				regex = charRegex;
				break;
			default:
				throw new BadVariableDefinitionException(num);
		}
		Matcher matcher = getMatcher(regex, line);
		int prevEnd = 0;
		while (matcher.find()) {
			int a = matcher.start();
			if (matcher.start() != prevEnd) {
				throw new BadVariableDefinitionException(num);
			}
			names.add(matcher.group(1));
			values.add(matcher.group(2));
			if (isFinal && matcher.group(2) == null) {
				throw new BadVariableDefinitionException(num);
			}
			if (matcher.group(2) != null && !type.equals(BOOLEAN) && (matcher.group(2).equals(FALSE) ||
                    matcher.group(2).equals(TRUE))) {
				throw new BadVariableDefinitionException(num);
			}
			prevEnd = matcher.end();
		}
		if (prevEnd != line.length()) {
			throw new BadVariableDefinitionException(num);
		}

	}

    /**
     * Returns whether the variables declared in this line are final.
     * @return Whether the variables declared in this line are final.
     */
	public boolean isFinal() {
		return isFinal;
	}

    /**
     * Returns the type of variables declared in this line.
     * @return The type of variables declared in this line.
     */
	public String getType() {
		return type;
	}

    /**
     * Returns the values declared in this line.
     * Can be null (if no value was assigned), a name (in case of a variable), or a type (in case of a
     * literal value).
     * @return The values declared in this line.
     */
	public ArrayList<String> getValues() {
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) != null) {
				Matcher matcher = LineInterpreter.getMatcher(validVarNameRegex, values.get(i));
				if (!matcher.matches() || values.get(i).equals("true") || values.get(i).equals("false")) {
					// the assignment is a legal value
					values.set(i, type);
				}

			}
		}
		return values;
	}

	/**
	 * Returns the names of the variables declared in this line.
	 * @return The names of the variables declared in this line.
	 */
	public ArrayList<String> getNames() {
		return names;
	}
}
