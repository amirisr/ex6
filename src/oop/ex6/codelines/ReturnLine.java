package oop.ex6.codelines;

import java.util.regex.Matcher;

/**
 * This class describes an analyser for return lines.
 * @author Amir Israeli
 * @author Omer Binyamin
 */
public class ReturnLine {
	private static final String isLineRegex = "\\s*return\\s*;\\s*";

	/**
	 * Returns if a given line is a return line or not.
	 * @param line The line to analyze.
	 * @return true iff the line is a return line.
	 */
	static boolean isLine(String line) {
		Matcher matcher = LineInterpreter.getMatcher(isLineRegex, line);
		return matcher.matches();
	}
}
