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

    /**
     *
     * @param type
     * @return
     */
    public static VarTypes StringToType(String type)
    {
        switch (type)
        {
            case "int":
                return INT;
            case "double":
                return DOUBLE;
            case "String":
                return STRING;
            case "boolean":
                return BOOLEAN;
            case "char":
                return CHAR;
        }
        return null;
    }
}
