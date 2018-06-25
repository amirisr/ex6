package oop.ex6.codelines;

import oop.ex6.scopes.CompileException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Line {

    String line;
	int num;
	private static String isLineRegex;

	public Line(String line, int lineNum) throws CompileException{
		this.line = line;
		num = lineNum;
		processLine();

	}

	protected Line(String line, String regex)
	{
		this.line = line;
		isLineRegex = regex;
	}

	static Matcher getMatcher(String regex, String string){
		Pattern p = Pattern.compile(regex);
		return p.matcher(string);
	}

	abstract void processLine() throws CompileException;

}
