package oop.ex6.codelines;

import oop.ex6.scopes.BadVariableDefinition;
import oop.ex6.variables.VarTypes;
import oop.ex6.variables.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final String stringRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*(\".*\")\\s*)?,";
	private static final String intRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*(\\d+)\\s*)?,";
	private static final String doubleRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*(\\d+(?:\\.\\d+)?)\\s*)?,";
	private static final String charRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*('.')\\s*)?,";
	private static final String booleanRegex = "\\s*("+validVarNameRegex+")\\s*(?:=\\s*(true|false)\\s*)?,";

    public static final String FINAL = "final";

    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String STRING = "String";
    private static final String CHAR = "char";
    private static final String BOOLEAN = "boolean";

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

    private static Matcher getMatcher(String regex, String string) {
        Pattern p = Pattern.compile(regex);
        return p.matcher(string);
    }

    /**
     * Determines if a given line describes a variable declaration or not.
     * @param line The line to analyze.
     * @return true iff the line describes a variable declaration line.
     */
    public static boolean isLine(String line) {
        Matcher matcher = getMatcher(isLineRegex, line);
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
     * Returns an array list of all variables declared in the line.
     * @return An array list of all variables declared in the line.
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
			prevEnd = matcher.end();
		}
		if(prevEnd != line.length()){
			throw new BadVariableDefinition(num);
		}

	}


}
