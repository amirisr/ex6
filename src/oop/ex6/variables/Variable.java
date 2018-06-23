package oop.ex6.variables;

public class Variable {

    private VarTypes type;
    private String name;
    private boolean isInitialized;
    private boolean isFinal;

    public Variable(VarTypes varType, String varName, boolean isVarInitialized, boolean isVarFinal)
    {
        type = varType;
        name = varName;
        isInitialized = isVarInitialized;
        isFinal = isVarFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public VarTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
