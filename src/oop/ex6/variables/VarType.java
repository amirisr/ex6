package oop.ex6.variables;

/**
 * Enum describing every possible variable type in s-java.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public enum VarType
{
    INT,
    DOUBLE,
    STRING,
    BOOLEAN,
    CHAR;

    /**
     * Returns the reserved java word for each type.
     * @return The reserved java word for each type.
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
     * Matches a String to a VarType.
     * @param type The name of the type.
     * @return The corresponding VarType.
     */
    public static VarType StringToType(String type)
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

    /**
     * Takes two types and determines if the assignment of the second one to the first one is compatible.
     * @param typeToChange The type that will get the data.
     * @param newType The type that will give the data.
     * @return true iff the two types are compatibles.
     */
    public static boolean isAssignmentLegit(VarType typeToChange, VarType newType)
    {
        switch (typeToChange)
        {
            case INT:
                switch (newType)
                {
                    case INT:
                        return true;
                    case DOUBLE:
                        return false;
                    case BOOLEAN:
                        return false;
                    case CHAR:
                        return false;
                    case STRING:
                        return false;
                }
            case DOUBLE:
                switch (newType)
                {
                    case INT:
                        return true;
                    case DOUBLE:
                        return true;
                    case BOOLEAN:
                        return false;
                    case CHAR:
                        return false;
                    case STRING:
                        return false;
                }
            case BOOLEAN:
                switch (newType)
                {
                    case INT:
                        return true;
                    case DOUBLE:
                        return true;
                    case BOOLEAN:
                        return true;
                    case CHAR:
                        return false;
                    case STRING:
                        return false;
                }
            case CHAR:
                switch (newType)
                {
                    case INT:
                        return false;
                    case DOUBLE:
                        return false;
                    case BOOLEAN:
                        return false;
                    case CHAR:
                        return true;
                    case STRING:
                        return false;
                }
            case STRING:
                switch (newType)
                {
                    case INT:
                        return false;
                    case DOUBLE:
                        return false;
                    case BOOLEAN:
                        return false;
                    case CHAR:
                        return false;
                    case STRING:
                        return true;
                }
        }
        return false;
    }
}
