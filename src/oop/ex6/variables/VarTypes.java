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

    /**
     * Takes two types and determines if the assignment of the second one to the first one is compatible.
     * @param typeToChange The type that will get the data.
     * @param newType The type that will give the data.
     * @return true iff the two types are compatibles.
     */
    public static boolean isAssignmentLegit(VarTypes typeToChange, VarTypes newType)
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
