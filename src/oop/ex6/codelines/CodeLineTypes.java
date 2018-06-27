package oop.ex6.codelines;

/**
 * Enum describing every possible line in s-java.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public enum CodeLineTypes {
    EMPTY_LINE,
    COMMENT,
    OPEN_METHOD,
    METHOD_CALL,
    OPEN_IF_WHILE,
    CLOSE_SCOPE,
    VAR_ASSIGNMENT,
    VAR_DEFINITION,
    RETURN,
    ERROR;
}
