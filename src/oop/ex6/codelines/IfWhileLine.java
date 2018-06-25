package oop.ex6.codelines;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfWhileLine extends Line{
	static String isLineRegex = "\\s*(?:while|if).*{\\s*";
	static String isConditionLegalRegex = "\\s*(?:_\\w+|[a-zA-Z]\\w*|d+(?:\\.\\d+)?)\\s*(?:\\|{2}|&{2})";
	static final String IF = "if";
	static final String WHILE = "while";

	 IfWhileLine(String line){
	 	super(line);
	 }

	static boolean isLine(String line){
		Matcher matcher = getMatcher(isLineRegex, line);
		return matcher.matches();
	}

	 void processLine(){
	 	line = line.trim();
	 	removeIfWhile();
	 	extractCondition();
	 }

	 /*
	 remove the if or the while prefix
	  */
	 private void removeIfWhile(){
	 	if(line.startsWith(IF)){
	 		line = line.substring(IF.length());
		}
		else {
	 		line = line.substring(WHILE.length());
		}
		line = line.trim();
	 }

	 private void extractCondition(){
	 	line = line.substring(0,line.length()-1).trim(); // remove the '{' char
	 	if(!line.startsWith("(") || !line.endsWith(")")){
	 		// raise exception, condition is not bounded with parentheses
		}
		line = line.substring(1,line.length()-1)+"||";
	 	if(getMatcher())
	 }
}
