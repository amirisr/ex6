package oop.ex6.codelines;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Line {
	String line;
	private static String isLineRegex;

	public Line(String line){
		this.line = line;
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

	abstract void processLine();

	static boolean isLine(String line){
		Matcher matcher = getMatcher(isLineRegex, line);
		return matcher.matches();
	}
}
