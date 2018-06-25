package oop.ex6.codelines;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MethodDefLine extends Line {

	final static String isLineRegex = "\\s*void\\s+.*{\\s*";
	static String checkMethodNameRegex = "([a-zA-Z]\\w*)\\s*\\(.*\\)";
	static String checkParamLineRegex = "(?:\\s*(?:final)?\\s*(?:int|double|char|boolean|String)\\s+" +
												"(?:"+VarInitLine.validVarNameRegex+"))\\s*,)*";
	static String parseSingleParamRegex = "(int|double|char|boolean|String)\\s+("+VarInitLine.validVarNameRegex+")";
	String methodName;
	ArrayList<String> paramTypes;
	ArrayList<String> paramNames;
	ArrayList<String> paramIsFinal;

	MethodDefLine(String line){
		super(line);
	}

	void processLine(){
		line = line.trim();
		line = line.substring(4); // remove the 'void' prefix
		line = line.substring(0, line.length()-1); // remove '{'
		line = line.trim(); // we are left with <name> (...)
		checkName();
		checkParamNamesAndTypes();
	}

	private void checkName(){
		Matcher matcher = getMatcher(checkMethodNameRegex, line);
		if(!matcher.matches()){
			//raise exception, name is not up to standard, or the parameters' parentheses are missing
		}
		else{
			methodName = matcher.group(1);
			line = line.substring(methodName.length()); // remove name
			line = line.trim(); // left with (...)
		}
	}

	private void checkParamNamesAndTypes(){
		line = line.substring(1, line.length()-1) + ','; // remove the parentheses, add comma
		if(getMatcher(checkParamLineRegex, line).matches()){
			String[] params = line.split(","); // dissect list of parameters to single parameter declaration
			for (int i=0; i < params.length-1; i++){
				String param = params[i].trim();
				if(param.startsWith(VarInitLine.FINAL)){
					paramIsFinal.add("1");
					param = param.substring(VarInitLine.FINAL.length()).trim();
				}
				else{
					paramIsFinal.add("0");
				}
				// so far we have a parameter with no "final" prefix, but with type and name
				Matcher matcher = getMatcher(parseSingleParamRegex, param);
				if(!matcher.matches()){
					// raise exception, we shouldn't reach here
				}
				paramTypes.add(matcher.group(1));
				paramNames.add(matcher.group(2));
			}

		}
		else{
			//raise exception, the param line did not match syntax
		}

	}
}
