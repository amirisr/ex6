package oop.ex6.codelines;

import oop.ex6.scopes.BadMethodDefinitionException;
import oop.ex6.variables.VarTypes;
import oop.ex6.variables.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class describes an analyser for method definition lines.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public class MethodDefLine{

	private int num;
	private String line;
	private final static String isLineRegex = "\\s*void\\s+.*\\{\\s*";
	private final static String checkMethodNameRegex = "([a-zA-Z]\\w*)\\s*\\(.*\\)";
	private final static String checkParamLineRegex = "(?:\\s*(?:final)?\\s*" +
            "(?:int|double|char|boolean|String)" + "\\s+" + "(?:" + VarInitLine.validVarNameRegex + ")\\s*,)*";
	private final static String parseSingleParamRegex = "(int|double|char|boolean|String)\\s+(" + VarInitLine
            .validVarNameRegex + ")"; // use this to extract a parameter's name and type
	private String methodName;
	private ArrayList<String> paramTypes;
	private ArrayList<String> paramNames;
	private ArrayList<String> paramIsFinal;

	MethodDefLine(String line, int lineNum) throws BadMethodDefinitionException{
		this.line = line;
		num = lineNum;
		paramNames = new ArrayList<>();
		paramTypes = new ArrayList<>();
		paramIsFinal = new ArrayList<>();
		processLine();
	}

	static Matcher getMatcher(String regex, String string){
		Pattern p = Pattern.compile(regex);
		return p.matcher(string);
	}

	static boolean isLine(String line) {
		Matcher matcher = getMatcher(isLineRegex, line);
		return matcher.matches();
	}


	private void processLine() throws BadMethodDefinitionException{
		line = line.trim();
		line = line.substring(4); // remove the 'void' prefix
		line = line.substring(0, line.length() - 1); // remove '{'
		line = line.trim(); // we are left with <name> (...)
		checkName();
		checkParamNamesAndTypes();
	}

	/*
	identify the method's name
	 */
	private void checkName() throws BadMethodDefinitionException{
		Matcher matcher = getMatcher(checkMethodNameRegex, line);
		if (!matcher.matches()) {
			throw new BadMethodDefinitionException(num);
		} else {
			methodName = matcher.group(1);
			line = line.substring(methodName.length()); // remove name
			line = line.trim(); // left with (...)
		}
	}

	/*
	identify every parameter's name and type
	 */
	private void checkParamNamesAndTypes() throws BadMethodDefinitionException{
		line = line.substring(1, line.length() - 1) + ','; // remove the parentheses, add comma
		if (getMatcher(checkParamLineRegex, line).matches()) {
			String[] params = line.split(","); // dissect list of parameters to single parameter declaration
			for (int i = 0; i < params.length; i++) {
				String param = params[i].trim();
				if (param.startsWith(VarInitLine.FINAL)) {
					paramIsFinal.add("1");
					param = param.substring(VarInitLine.FINAL.length()).trim();
				} else {
					paramIsFinal.add("0");
				}
				// so far we have a parameter with no "final" prefix, but with type and name
				Matcher matcher = getMatcher(parseSingleParamRegex, param);
				if (!matcher.matches()) {
					throw new BadMethodDefinitionException(num);
				}
				paramTypes.add(matcher.group(1));
				paramNames.add(matcher.group(2));
			}

		} else if(line.equals(",")) {
			return;
		}
		else {
			throw new BadMethodDefinitionException(num);
		}

	}

	ArrayList<Variable> getParams(){
		ArrayList<Variable> params = new ArrayList<>();
		for(int i = 0; i < paramNames.size(); i++){
			params.add(new Variable(VarTypes.StringToType(paramTypes.get(i)), paramNames.get(i), true,
					paramIsFinal.get(i).equals("1")));
		}
		return params;
	}
}
