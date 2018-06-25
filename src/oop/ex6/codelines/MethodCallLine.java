package oop.ex6.codelines;

import oop.ex6.scopes.MethodCallException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MethodCallLine {
	private int num;
	private String line;
	private String methodName;
	private ArrayList<String> parameters = new ArrayList<>();
	static private final String isLineRegex = "\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*;\\s*";
	static private final String getNameRegex = "([a-zA-Z]\\w*)\\s*\\(.*\\)";
	static private final String getParameterRegex = "^\\s*(\".*\"|'.'|[+-]?\\d+(?:\\.\\d+)?|_\\w+|[a-zA-Z]\\w*)\\s*,";

	MethodCallLine(String line, int lineNum) throws MethodCallException{
		this.line = line;
		num = lineNum;
		processLine();
	}

	static boolean isLine(String line){
		return LineInterpreter.getMatcher(isLineRegex, line).matches();
	}

	private void processLine() throws MethodCallException{
		line = line.trim();
		line = line.substring(0,line.length()-1).trim();
		extractName();
		extractParams();

	}

	private void extractName() throws MethodCallException{
		Matcher matcher = LineInterpreter.getMatcher(getNameRegex, line);
		if(!matcher.matches()){
			throw new MethodCallException(num);
		}
		methodName = matcher.group(1);
		line = line.substring(methodName.length()).trim(); // remove method name
	}

	private void extractParams() throws MethodCallException{
		line = line.substring(1, line.length() - 1) + ','; // remove the parentheses, add comma
		Matcher matcher = LineInterpreter.getMatcher(getParameterRegex, line);
		while(matcher.find()){
			parameters.add(matcher.group(1));
			line = line.substring(matcher.group(0).length());
		}
		if(line.length() != 0){
			throw new MethodCallException(num);
		}
		for(int i = 0; i < parameters.size(); i++){
			parameters.set(i, VarAssignLine.checkAssignment(parameters.get(i))); // get the type of the
			// assignment
			if(parameters.get(i) == null){
				throw new MethodCallException(num);
			}
		}
	}

	ArrayList<String> getParameters(){
		return parameters;
	}

}
