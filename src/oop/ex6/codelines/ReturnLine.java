package oop.ex6.codelines;

import java.util.regex.Matcher;

public class ReturnLine {
	private static final String isLineRegex = "\\s*return\\s*;\\s*";

	static boolean isLine(String line) {
		Matcher matcher = LineInterpreter.getMatcher(isLineRegex, line);
		return matcher.matches();
	}
}
