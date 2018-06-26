package oop.ex6.codelines;

import oop.ex6.scopes.MethodCallException;

import java.util.ArrayList;
import java.util.regex.Matcher;


/**
 * This class describes an analyser for method calls lines.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public class MethodCallLine {
	private int num;
	private String line;
	private String methodName;
	private ArrayList<String> parameters = new ArrayList<>();
	private static final String isLineRegex = "\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*;\\s*";
	private static final String getNameRegex = "([a-zA-Z]\\w*)\\s*\\(.*\\)";
	private static final String getParameterRegex = "\\s*(\".*\"|'.'|[+-]?\\d+(?:\\.\\d+)" +
            "?|_\\w+|[a-zA-Z]\\w*)\\s*,";

    /**
     * Constructor for the method calls analyser.
     * @param line The line to analyze.
     * @param lineNum The line number to analyze.
     * @throws MethodCallException If the call is not legal.
     */
	public MethodCallLine(String line, int lineNum) throws MethodCallException{
		this.line = line;
		num = lineNum;
		processLine();
	}

    /**
     * Returns if the line is a method call line.
     * @param line The line to analyze.
     * @return true iff the line is a method call line.
     */
	public static boolean isLine(String line){
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
		if(!matcher.matches()){ // name is not up to standard
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

    /**
     * Returns the arguments of the call.
     * @return The arguments of the call.
     */
	public ArrayList<String> getParameters(){
		return parameters;
	}

}
