
package oop.ex6.scopes;

import oop.ex6.variables.Variable;

import java.util.ArrayList;

public class MethodScope extends Scope {

    private ArrayList<Variable> params;

    public MethodScope(GlobalScope global, int startLine, int endLine)
    {
        super(global, startLine, endLine);
        initializeParameters();
    }

    @Override
    public boolean testScope() {
        return false;
        //TODO
    }

    private void initializeParameters()
    {
        params = new ArrayList<Variable>();
        //TODO
    }

    public boolean isCallValid(String line) {
        return false;
        //TODO
    }
}
