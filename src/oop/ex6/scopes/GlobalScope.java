package oop.ex6.scopes;

import oop.ex6.codelines.LineInterpreter;
import oop.ex6.variables.Variable;

import java.util.ArrayList;

public class GlobalScope extends Scope
{
    private ArrayList<MethodScope> methods;
    private String[] codeLines;

    public GlobalScope(String[] lines){
        super(null, 0, lines.length - 1);
        methods = new ArrayList<MethodScope>();
        codeLines = lines;
    }

    public ArrayList<MethodScope> getMethods() {
        return methods;
    }

    public void addMethod(MethodScope method) {
        methods.add(method);
    }

    @Override
    public boolean testScope() {
        //TODO
        for (int i = getStart(); i <= getEnd(); i++)
        {
            String line = codeLines[i];
            int methodStart = -1;
            int count = 0;
            switch (LineInterpreter.getLineType(line))
            {
                case EMPTY_LINE:
                case COMMENT:
                    break;
                case OPEN_IF:
                case OPEN_WHILE:
                    count++;
                    break;
                case OPEN_METHOD:
                    if (count > 0)
                    {
                        return false;
                    }
                    else
                    {
                        methodStart = i;
                        count++;
                    }
                    break;
                case CLOSE_SCOPE:
                    count--;
                    if (count == 0)
                    {
                        addMethod(LineInterpreter.createMethod(this, methodStart, i));
                    }
                    if (count < 0)
                    {
                        return false;
                    }
                    break;
                case VAR_ASSIGNMENT:
                    if (!LineInterpreter.isAssignmentLegal(this, line, i))
                    {
                        return false;
                    }
                    break;
                case VAR_DEFINITION:
                    ArrayList<Variable> tmp = LineInterpreter.getVariables(line, i);
                    addVariable(tmp);
                    break;
                default: //including method call
                    return false;

            }
        }

        for (Scope scope : methods)
        {
            if (!scope.testScope())
            {
                return false;
            }
        }

        return true;
    }
}
