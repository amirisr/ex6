
package oop.ex6.scopes;

import oop.ex6.codelines.LineInterpreter;
import oop.ex6.variables.Variable;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class MethodScope extends Scope {

    private ArrayList<Variable> params;

    public MethodScope(GlobalScope global, int startLine, int endLine) throws MethodDefinitionException
    {
        super(global, startLine, endLine);
        params = LineInterpreter.getParameters(getGlobalScope().getCodeLines()[getStart()], getStart());
    }


    @Override
    public void testScope() throws MethodDefinitionException, MethodDefinedInsideScopeException,
            IfWhileConditionException, IfWhileDefinedOutsideScopeException, OverScopeClosersException,
            BadAssignmentException, MethodCallInGlobalScepoException, SyntaxException, MethodCallException {
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
