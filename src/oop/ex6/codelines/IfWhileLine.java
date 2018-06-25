package oop.ex6.codelines;

public class IfWhileLine extends Line{
	static String isLineRegex = "\\s*(?:while|if).*{\\s*";
	static final String IF = "if";
	static final String WHILE = "while";

	 IfWhileLine(String line){
	 	super(line);
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

	 }
}
