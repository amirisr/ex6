package oop.ex6.variables;

public enum VarTypes {
    INT,
    DOUBLE,
    STRING,
    BOOLEAN,
    CHAR;

    /**
     * Returns the reserved java word for each type.
     * @return
     */
    @Override
    public String toString() {
        switch (this)
        {
            case INT:
                return "int";
            case DOUBLE:
                return "double";
            case STRING:
                return "String";
            case BOOLEAN:
                return "boolean";
            case CHAR:
                return "char";
        }
        return "";
    }
}
