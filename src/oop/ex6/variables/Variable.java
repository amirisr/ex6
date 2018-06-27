package oop.ex6.variables;

/**
 * A class describing a variable in this exercise.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class Variable {

	private VarType type;
	private String name;
	private boolean isInitialized;
	private boolean isFinal;

	/**
	 * A constructor for a variable.
	 * @param varType          The variable's type.
	 * @param varName          The variable's name.
	 * @param isVarInitialized Does the variable has a value.
	 * @param isVarFinal       Was the variable defined as final.
	 */
	public Variable(VarType varType, String varName, boolean isVarInitialized, boolean isVarFinal) {
		type = varType;
		name = varName;
		isInitialized = isVarInitialized;
		isFinal = isVarFinal;
	}

	/**
	 * Returns whether this variable is final or not.
	 * @return Whether this variable is final or not.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Returns whether this variable holds a value or not.
	 * @return Whether this variable holds a value or not.
	 */
	public boolean isInitialized() {
		return isInitialized;
	}

	/**
	 * Returns the variable's type.
	 * @return The variable's type.
	 */
	public VarType getType() {
		return type;
	}

	/**
	 * Returns the variable's name.
	 * @return The variable's name.
	 */
	public String getName() {
		return name;
	}

    /**
     * Sets the variable as initialized.
     */
	public void setAsInitialized()
    {
        isInitialized = true;
    }

    /**
     * Clones/Copies the variable.
     * @return A new identical Variable.
     */
    public Variable cloneVariable()
    {
        return new Variable(type, name, isInitialized, isFinal);
    }
}
