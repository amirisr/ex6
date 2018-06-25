package oop.ex6.codelines;

public class VarAssignLine {
	private final String isLineRegex = "\\s*\\w*\\s*=.*;\\s*";
	private final String line;
	private final int num;
	VarAssignLine(String line, int lineNum){
		this.line = line;
		num = lineNum;
		processLine();
	}

	boolean isLine(String line){
		return LineInterpreter.getMatcher(line, isLineRegex).matches();
	}

	private void processLine(){

	}
}
