package oop.ex6.scopes;

import oop.ex6.codelines.*;
import oop.ex6.variables.*;

import java.util.*;

public class IfWhileScope extends Scope {

    public IfWhileScope(Scope father, int startLine, int endLine) throws BadIfWhileConditionException
    {
        super(father, startLine, endLine);
        LineInterpreter.verifyIfWhileCondition(this, getGlobalScope().getCodeLines()[getStart()], getStart());
    }

    @Override
    public void testScope() throws CompileException {
        String[] codeLines = getGlobalScope().getCodeLines();
        int scopeStart = -1;
        int count = 0;
        for (int i = getStart() + 1; i < getEnd(); i++)
        {
            String line = codeLines[i];
            switch (LineInterpreter.getLineType(line))
            {
                case EMPTY_LINE:
                case COMMENT:
                case RETURN:
                    break;
                case OPEN_IF_WHILE:
                    if (count > 0) {
                        count++;
                    }
                    else {
                        scopeStart = i;
                        count++;
                    }
                    break;
                case METHOD_CALL:
                    if (count == 0) {
                        LineInterpreter.verifyMethodCall(this, i);
                    }
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0)
                    {
                        Scope tmp = new IfWhileScope(this, scopeStart, i);
                        tmp.testScope();
                    }
                    if (count < 0) {
                        throw new NumberOfScopeClosersException(i);
                    }
                    break;
                case VAR_ASSIGNMENT:
                    if (count == 0) {
                        LineInterpreter.verifyAssignment(this, i);
                    }
                    break;
                case VAR_DEFINITION:
                    if (count == 0) {
                        ArrayList<Variable> tmp = LineInterpreter.getVariables(this, i);
                        addVariablesFromArrayList(tmp, i);
                    }
                    break;
                case OPEN_METHOD:
                    throw new MethodDefinedInsideScopeException(i);
                default:
                    throw new SyntaxException(i);

            }
        }

        if (count != 0 || LineInterpreter.getLineType(codeLines[getEnd()]) != CodeLineTypes.CLOSE_SCOPE)
        {
            throw new NumberOfScopeClosersException(getEnd());
        }

    }
}
