package oop.ex6.scopes;

import oop.ex6.codelines.*;
import oop.ex6.variables.*;

import java.util.*;

public class IfWhileScope extends Scope {

    public IfWhileScope(Scope father, int startLine, int endLine) throws BadIfWhileConditionException
    {
        super(father, startLine, endLine);
        LineInterpreter.verifyIfWhileCondition(getGlobalScope().getCodeLines()[getStart()], getStart());
    }

    @Override
    public void testScope() throws CompileException {
        String[] codeLines = getGlobalScope().getCodeLines();
        for (int i = getStart() + 1; i <= getEnd(); i++)
        {
            String line = codeLines[i];
            int scopeStart = -1;
            int count = 0;
            switch (LineInterpreter.getLineType(line))
            {
                case EMPTY_LINE:
                case COMMENT:
                    break;
                case OPEN_IF_WHILE:
                    if (count > 0) {
                        count++;
                    }
                    else {
                        scopeStart = i;
                    }
                    break;
                case METHOD_CALL:
                    LineInterpreter.verifyMethodCall(this, line, i);
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0)
                    {
                        Scope tmp = new IfWhileScope(this, scopeStart, i);
                        tmp.testScope();
                    }
                    if (count < 0)
                    {
                        throw new OverScopeClosersException(i);
                    }
                    break;
                case VAR_ASSIGNMENT:
                    LineInterpreter.verifyAssignment(this, line, i);
                    break;
                case VAR_DEFINITION:
                    ArrayList<Variable> tmp = LineInterpreter.getVariables(line, i);
                    addVariablesFromArrayList(tmp);
                    break;
                case OPEN_METHOD:
                    throw new MethodDefinedInsideScopeException(i);
                default:
                    throw new SyntaxException(i);

            }
        }
    }
}
