package oop.ex6.codelines;

import oop.ex6.scopes.BadVariableDefinition;
import oop.ex6.variables.*;

import java.util.ArrayList;
import java.util.regex.*;

import static oop.ex6.codelines.LineInterpreter.getMatcher;

/**
 * This class describes an analyser for variable declaration lines.
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
    private static final String isLineRegex = "\\s*(?:final\\s)?\\s*(?:int|double|char|boolean|String)\\s+.*;\\s*";
    public static final String validVarNameRegex = "_\\w+|[a-zA-Z]\\w*";

	private static final String stringRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*(\".*\"|"+validVarNameRegex+")\\s*)?,";
	private static final String intRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*([+-]?\\d+|"+validVarNameRegex+")\\s*)?,";
	private static final String doubleRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*([+-]?\\d+(?:\\.\\d+)"+
													  "?|"+validVarNameRegex+")\\s*)?,";
	private static final String charRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*('.'|"+validVarNameRegex+")\\s*)?,";
	private static final String booleanRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*" +
													   "(true|false|[+-]?\\d+(?:\\.\\d+)?|"+validVarNameRegex+")\\s*)?,";

	static final String FINAL = "final";

    static final String INT = "int";
    static final String DOUBLE = "double";
    static final String STRING = "String";
    static final String CHAR = "char";
    static final String BOOLEAN = "boolean";

    /**
     * Constructor for a variable declaration line analyser.
     * @param line The line to analyze.
     * @param lineNum The line number in its code.
     * @throws BadVariableDefinition In case the line is not legit line.
     */
    public VarInitLine(String line, int lineNum) throws BadVariableDefinition {
        this.line = line;
        num = lineNum;
        names = new ArrayList<String>();
        values = new ArrayList<String>();
        processLine();
    }

    /**
     * Determines if a given line describes a variable declaration or not.
     * @param line The line to analyze.
     * @return true iff the line describes a variable declaration line.
     */
    public static boolean isLine(String line) {
        Matcher matcher = LineInterpreter.getMatcher(isLineRegex, line);
        return matcher.matches();
    }

	private void processLine() throws BadVariableDefinition{
		line = line.trim(); // remove excess spaces
		line = line.substring(0,line.length()-1) + ","; // replace the ';' with ','
		checkFinal();
		getVarType();
		findVarsAndValues();
	}

    /**
     * Returns the variables initialized in the line.
     * @return The variables initialized in the line.
     */
	public ArrayList<Variable> getVars() {
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
	private void findVarsAndValues() throws BadVariableDefinition{
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
				throw new BadVariableDefinition(num);
		}
		Matcher matcher = getMatcher(regex, line);
		int prevEnd = 0;
		while(matcher.find()){
			int a = matcher.start();
			if(matcher.start() != prevEnd){
				throw new BadVariableDefinition(num);
			}
			names.add(matcher.group(1));
			values.add(matcher.group(2));
			if(isFinal && matcher.group(2) == null){
				throw new BadVariableDefinition(num);
			}
			prevEnd = matcher.end();
		}
		if(prevEnd != line.length()){
			throw new BadVariableDefinition(num);
		}

	}

	public boolean isFinal() {
		return isFinal;
	}

	public String getType(){
		return type;
	}

	public ArrayList<String> getValues() {
		for(int i = 0; i < values.size(); i++) {
            if (values.get(i) != null) {
                Matcher matcher = LineInterpreter.getMatcher(validVarNameRegex, values.get(i));
                if (!matcher.matches()) { // the assignment is a legal value
                    values.set(i, type);
                }

            }
        }
		return values;
	}

	public ArrayList<String> getNames() {
		return names;
	}
}
